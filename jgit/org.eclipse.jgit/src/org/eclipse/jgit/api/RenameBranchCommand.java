/*
 * Copyright (C) 2010, Mathias Kinzler <mathias.kinzler@sap.com>
 * Copyright (C) 2010, Chris Aniszczyk <caniszczyk@gmail.com> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

/**
 * Used to rename branches.
 *
 * @see <a
 *      href="http://www.kernel.org/pub/software/scm/git/docs/git-branch.html"
 *      >Git documentation about Branch</a>
 */
public class RenameBranchCommand extends GitCommand<Ref> {
	private String oldName;

	private String newName;

	/**
	 * <p>
	 * Constructor for RenameBranchCommand.
	 * </p>
	 *
	 * @param repo
	 *            the {@link org.eclipse.jgit.lib.Repository}
	 */
	protected RenameBranchCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Ref call() throws GitAPIException, RefNotFoundException, InvalidRefNameException,
			RefAlreadyExistsException, DetachedHeadException {
		checkCallable();

		if (newName == null) {
			throw new InvalidRefNameException(MessageFormat.format(JGitText
					.get().branchNameInvalid, "<null>")); //$NON-NLS-1$
		}
		try {
			String fullOldName;
			String fullNewName;
			if (oldName != null) {
				// Don't just rely on findRef -- if there are local and remote
				// branches with the same name, and oldName is a short name, it
				// does not uniquely identify the ref and we might end up
				// renaming the wrong branch or finding a tag instead even
				// if a unique branch for the name exists!
				//
				// OldName may be a either a short or a full name.
				Ref ref = repo.exactRef(oldName);
				if (ref == null) {
					ref = repo.exactRef(Constants.R_HEADS + oldName);
					Ref ref2 = repo.exactRef(Constants.R_REMOTES + oldName);
					if (ref != null && ref2 != null) {
						throw new RefNotFoundException(MessageFormat.format(
								JGitText.get().renameBranchFailedAmbiguous,
								oldName, ref.getName(), ref2.getName()));
					} else if (ref == null) {
						if (ref2 != null) {
							ref = ref2;
						} else {
							throw new RefNotFoundException(MessageFormat.format(
									JGitText.get().refNotResolved, oldName));
						}
					}
				}
				fullOldName = ref.getName();
			} else {
				fullOldName = repo.getFullBranch();
				if (fullOldName == null) {
					throw new NoHeadException(
							JGitText.get().invalidRepositoryStateNoHead);
				}
				if (ObjectId.isId(fullOldName))
					throw new DetachedHeadException();
			}

			if (fullOldName.startsWith(Constants.R_REMOTES)) {
				fullNewName = Constants.R_REMOTES + newName;
			} else if (fullOldName.startsWith(Constants.R_HEADS)) {
				fullNewName = Constants.R_HEADS + newName;
			} else {
				throw new RefNotFoundException(MessageFormat.format(
						JGitText.get().renameBranchFailedNotABranch,
						fullOldName));
			}

			if (!Repository.isValidRefName(fullNewName)) {
				throw new InvalidRefNameException(MessageFormat.format(JGitText
						.get().branchNameInvalid, fullNewName));
			}
			if (repo.exactRef(fullNewName) != null) {
				throw new RefAlreadyExistsException(MessageFormat
						.format(JGitText.get().refAlreadyExists1, fullNewName));
			}
			RefRename rename = repo.renameRef(fullOldName, fullNewName);
			Result renameResult = rename.rename();

			setCallable(false);

			if (Result.RENAMED != renameResult) {
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().renameBranchUnexpectedResult, renameResult
						.name()));
			}
			if (fullNewName.startsWith(Constants.R_HEADS)) {
				String shortOldName = fullOldName.substring(Constants.R_HEADS
						.length());
				final StoredConfig repoConfig = repo.getConfig();
				// Copy all configuration values over to the new branch
				for (String name : repoConfig.getNames(
						ConfigConstants.CONFIG_BRANCH_SECTION, shortOldName)) {
					String[] values = repoConfig.getStringList(
							ConfigConstants.CONFIG_BRANCH_SECTION,
							shortOldName, name);
					if (values.length == 0) {
						continue;
					}
					// Keep any existing values already configured for the
					// new branch name
					String[] existing = repoConfig.getStringList(
							ConfigConstants.CONFIG_BRANCH_SECTION, newName,
							name);
					if (existing.length > 0) {
						String[] newValues = new String[values.length
								+ existing.length];
						System.arraycopy(existing, 0, newValues, 0,
								existing.length);
						System.arraycopy(values, 0, newValues, existing.length,
								values.length);
						values = newValues;
					}

					repoConfig.setStringList(
							ConfigConstants.CONFIG_BRANCH_SECTION, newName,
							name, Arrays.asList(values));
				}
				repoConfig.unsetSection(ConfigConstants.CONFIG_BRANCH_SECTION,
						shortOldName);
				repoConfig.save();
			}

			Ref resultRef = repo.exactRef(fullNewName);
			if (resultRef == null) {
				throw new JGitInternalException(
						JGitText.get().renameBranchFailedUnknownReason);
			}
			return resultRef;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage(), ioe);
		}
	}

	/**
	 * Sets the new short name of the branch.
	 * <p>
	 * The full name is constructed using the prefix of the branch to be renamed
	 * defined by either {@link #setOldName(String)} or HEAD. If that old branch
	 * is a local branch, the renamed branch also will be, and if the old branch
	 * is a remote branch, so will be the renamed branch.
	 * </p>
	 *
	 * @param newName
	 *            the new name
	 * @return this instance
	 */
	public RenameBranchCommand setNewName(String newName) {
		checkCallable();
		this.newName = newName;
		return this;
	}

	/**
	 * Sets the old name of the branch.
	 * <p>
	 * {@code oldName} may be a short or a full name. Using a full name is
	 * recommended to unambiguously identify the branch to be renamed.
	 * </p>
	 *
	 * @param oldName
	 *            the name of the branch to rename; if not set, the currently
	 *            checked out branch (if any) will be renamed
	 * @return this instance
	 */
	public RenameBranchCommand setOldName(String oldName) {
		checkCallable();
		this.oldName = oldName;
		return this;
	}
}
