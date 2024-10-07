/*
 * Copyright (C) 2012, GitHub Inc. and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.R_STASH;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.RefDirectory;
import org.eclipse.jgit.internal.storage.file.ReflogWriter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FileUtils;

/**
 * Command class to delete a stashed commit reference
 * <p>
 * Currently only supported on a traditional file repository using
 * one-file-per-ref reflogs.
 *
 * @see <a href="http://www.kernel.org/pub/software/scm/git/docs/git-stash.html"
 *      >Git documentation about Stash</a>
 * @since 2.0
 */
public class StashDropCommand extends GitCommand<ObjectId> {

	private int stashRefEntry;

	private boolean all;

	/**
	 * Constructor for StashDropCommand.
	 *
	 * @param repo
	 *            a {@link org.eclipse.jgit.lib.Repository} object.
	 */
	public StashDropCommand(Repository repo) {
		super(repo);
		if (!(repo.getRefDatabase() instanceof RefDirectory)) {
			throw new UnsupportedOperationException(
					JGitText.get().stashDropNotSupported);
		}
	}

	/**
	 * Set the stash reference to drop (0-based).
	 * <p>
	 * This will default to drop the latest stashed commit (stash@{0}) if
	 * unspecified
	 *
	 * @param stashRef
	 *            the 0-based index of the stash reference
	 * @return {@code this}
	 */
	public StashDropCommand setStashRef(int stashRef) {
		if (stashRef < 0)
			throw new IllegalArgumentException();

		stashRefEntry = stashRef;
		return this;
	}

	/**
	 * Set whether to drop all stashed commits
	 *
	 * @param all
	 *            {@code true} to drop all stashed commits, {@code false} to
	 *            drop only the stashed commit set via calling
	 *            {@link #setStashRef(int)}
	 * @return {@code this}
	 */
	public StashDropCommand setAll(boolean all) {
		this.all = all;
		return this;
	}

	private Ref getRef() throws GitAPIException {
		try {
			return repo.exactRef(R_STASH);
		} catch (IOException e) {
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().cannotRead, R_STASH), e);
		}
	}

	private RefUpdate createRefUpdate(Ref stashRef) throws IOException {
		RefUpdate update = repo.updateRef(R_STASH);
		update.disableRefLog();
		update.setExpectedOldObjectId(stashRef.getObjectId());
		update.setForceUpdate(true);
		return update;
	}

	private void deleteRef(Ref stashRef) {
		try {
			Result result = createRefUpdate(stashRef).delete();
			if (Result.FORCED != result)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().stashDropDeleteRefFailed, result));
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashDropFailed, e);
		}
	}

	private void updateRef(Ref stashRef, ObjectId newId) {
		try {
			RefUpdate update = createRefUpdate(stashRef);
			update.setNewObjectId(newId);
			Result result = update.update();
			switch (result) {
			case FORCED:
			case NEW:
			case NO_CHANGE:
				return;
			default:
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().updatingRefFailed, R_STASH, newId,
						result));
			}
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashDropFailed, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Drop the configured entry from the stash reflog and return value of the
	 * stash reference after the drop occurs
	 */
	@Override
	public ObjectId call() throws GitAPIException {
		checkCallable();

		Ref stashRef = getRef();
		if (stashRef == null)
			return null;

		if (all) {
			deleteRef(stashRef);
			return null;
		}

		List<ReflogEntry> entries;
		try {
			ReflogReader reader = repo.getReflogReader(R_STASH);
			if (reader == null) {
				throw new RefNotFoundException(MessageFormat
						.format(JGitText.get().refNotResolved, stashRef));
			}
			entries = reader.getReverseEntries();
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashDropFailed, e);
		}

		if (stashRefEntry >= entries.size())
			throw new JGitInternalException(
					JGitText.get().stashDropMissingReflog);

		if (entries.size() == 1) {
			deleteRef(stashRef);
			return null;
		}

		RefDirectory refdb = (RefDirectory) repo.getRefDatabase();
		ReflogWriter writer = new ReflogWriter(refdb, true);
		String stashLockRef = ReflogWriter.refLockFor(R_STASH);
		File stashLockFile = refdb.logFor(stashLockRef);
		File stashFile = refdb.logFor(R_STASH);
		if (stashLockFile.exists())
			throw new JGitInternalException(JGitText.get().stashDropFailed,
					new LockFailedException(stashFile));

		entries.remove(stashRefEntry);
		ObjectId entryId = ObjectId.zeroId();
		try {
			for (int i = entries.size() - 1; i >= 0; i--) {
				ReflogEntry entry = entries.get(i);
				writer.log(stashLockRef, entryId, entry.getNewId(),
						entry.getWho(), entry.getComment());
				entryId = entry.getNewId();
			}
			try {
				FileUtils.rename(stashLockFile, stashFile,
						StandardCopyOption.ATOMIC_MOVE);
			} catch (IOException e) {
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().renameFileFailed,
								stashLockFile.getPath(), stashFile.getPath()),
						e);
			}
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashDropFailed, e);
		}
		updateRef(stashRef, entryId);

		try {
			Ref newStashRef = repo.exactRef(R_STASH);
			return newStashRef != null ? newStashRef.getObjectId() : null;
		} catch (IOException e) {
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().cannotRead, R_STASH), e);
		}
	}
}
