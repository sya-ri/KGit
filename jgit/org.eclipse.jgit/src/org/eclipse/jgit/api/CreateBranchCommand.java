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

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Used to create a local branch.
 *
 * @see <a
 *      href="http://www.kernel.org/pub/software/scm/git/docs/git-branch.html"
 *      >Git documentation about Branch</a>
 */
public class CreateBranchCommand extends GitCommand<Ref> {
	private String name;

	private boolean force = false;

	private SetupUpstreamMode upstreamMode;

	private String startPoint = HEAD;

	private RevCommit startCommit;

	/**
	 * The modes available for setting up the upstream configuration
	 * (corresponding to the --set-upstream, --track, --no-track options
	 *
	 */
	public enum SetupUpstreamMode {
		/**
		 * Corresponds to the --track option
		 */
		TRACK,
		/**
		 * Corresponds to the --no-track option
		 */
		NOTRACK,
		/**
		 * Corresponds to the --set-upstream option
		 */
		SET_UPSTREAM;
	}

	/**
	 * Constructor for CreateBranchCommand
	 *
	 * @param repo
	 *            the {@link org.eclipse.jgit.lib.Repository}
	 */
	protected CreateBranchCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Ref call() throws GitAPIException, RefAlreadyExistsException,
			RefNotFoundException, InvalidRefNameException {
		checkCallable();
		processOptions();
		try (RevWalk revWalk = new RevWalk(repo)) {
			Ref refToCheck = repo.findRef(name);
			boolean exists = refToCheck != null
					&& refToCheck.getName().startsWith(R_HEADS);
			if (!force && exists)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadyExists1, name));

			ObjectId startAt = getStartPointObjectId();
			String startPointFullName = null;
			if (startPoint != null) {
				Ref baseRef = repo.findRef(startPoint);
				if (baseRef != null)
					startPointFullName = baseRef.getName();
			}

			// determine whether we are based on a commit,
			// a branch, or a tag and compose the reflog message
			String refLogMessage;
			String baseBranch = ""; //$NON-NLS-1$
			if (startPointFullName == null) {
				String baseCommit;
				if (startCommit != null)
					baseCommit = startCommit.getShortMessage();
				else {
					RevCommit commit = revWalk.parseCommit(repo
							.resolve(getStartPointOrHead()));
					baseCommit = commit.getShortMessage();
				}
				if (exists)
					refLogMessage = "branch: Reset start-point to commit " //$NON-NLS-1$
							+ baseCommit;
				else
					refLogMessage = "branch: Created from commit " + baseCommit; //$NON-NLS-1$

			} else if (startPointFullName.startsWith(R_HEADS)
					|| startPointFullName.startsWith(Constants.R_REMOTES)) {
				baseBranch = startPointFullName;
				if (exists)
					refLogMessage = "branch: Reset start-point to branch " //$NON-NLS-1$
							+ startPointFullName; // TODO
				else
					refLogMessage = "branch: Created from branch " + baseBranch; //$NON-NLS-1$
			} else {
				startAt = revWalk.peel(revWalk.parseAny(startAt));
				if (exists)
					refLogMessage = "branch: Reset start-point to tag " //$NON-NLS-1$
							+ startPointFullName;
				else
					refLogMessage = "branch: Created from tag " //$NON-NLS-1$
							+ startPointFullName;
			}

			RefUpdate updateRef = repo.updateRef(R_HEADS + name);
			updateRef.setNewObjectId(startAt);
			updateRef.setRefLogMessage(refLogMessage, false);
			Result updateResult;
			if (exists && force)
				updateResult = updateRef.forceUpdate();
			else
				updateResult = updateRef.update();

			setCallable(false);

			boolean ok = false;
			switch (updateResult) {
			case NEW:
				ok = !exists;
				break;
			case NO_CHANGE:
			case FAST_FORWARD:
			case FORCED:
				ok = exists;
				break;
			default:
				break;
			}

			if (!ok)
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().createBranchUnexpectedResult, updateResult
						.name()));

			Ref result = repo.findRef(name);
			if (result == null)
				throw new JGitInternalException(
						JGitText.get().createBranchFailedUnknownReason);

			if (baseBranch.length() == 0) {
				return result;
			}

			// if we are based on another branch, see
			// if we need to configure upstream configuration: first check
			// whether the setting was done explicitly
			boolean doConfigure;
			if (upstreamMode == SetupUpstreamMode.SET_UPSTREAM
					|| upstreamMode == SetupUpstreamMode.TRACK)
				// explicitly set to configure
				doConfigure = true;
			else if (upstreamMode == SetupUpstreamMode.NOTRACK)
				// explicitly set to not configure
				doConfigure = false;
			else {
				// if there was no explicit setting, check the configuration
				String autosetupflag = repo.getConfig().getString(
						ConfigConstants.CONFIG_BRANCH_SECTION, null,
						ConfigConstants.CONFIG_KEY_AUTOSETUPMERGE);
				if ("false".equals(autosetupflag)) { //$NON-NLS-1$
					doConfigure = false;
				} else if ("always".equals(autosetupflag)) { //$NON-NLS-1$
					doConfigure = true;
				} else {
					// in this case, the default is to configure
					// only in case the base branch was a remote branch
					doConfigure = baseBranch.startsWith(Constants.R_REMOTES);
				}
			}

			if (doConfigure) {
				StoredConfig config = repo.getConfig();

				String remoteName = repo.getRemoteName(baseBranch);
				if (remoteName != null) {
					String branchName = repo
							.shortenRemoteBranchName(baseBranch);
					config
							.setString(ConfigConstants.CONFIG_BRANCH_SECTION,
									name, ConfigConstants.CONFIG_KEY_REMOTE,
									remoteName);
					config.setString(ConfigConstants.CONFIG_BRANCH_SECTION,
							name, ConfigConstants.CONFIG_KEY_MERGE,
							Constants.R_HEADS + branchName);
				} else {
					// set "." as remote
					config.setString(ConfigConstants.CONFIG_BRANCH_SECTION,
							name, ConfigConstants.CONFIG_KEY_REMOTE, "."); //$NON-NLS-1$
					config.setString(ConfigConstants.CONFIG_BRANCH_SECTION,
							name, ConfigConstants.CONFIG_KEY_MERGE, baseBranch);
				}
				config.save();
			}
			return result;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage(), ioe);
		}
	}

	private ObjectId getStartPointObjectId() throws AmbiguousObjectException,
			RefNotFoundException, IOException {
		if (startCommit != null)
			return startCommit.getId();
		String startPointOrHead = getStartPointOrHead();
		ObjectId result = repo.resolve(startPointOrHead);
		if (result == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved, startPointOrHead));
		return result;
	}

	private String getStartPointOrHead() {
		return startPoint != null ? startPoint : HEAD;
	}

	private void processOptions() throws InvalidRefNameException {
		if (name == null
				|| !Repository.isValidRefName(R_HEADS + name)
				|| !isValidBranchName(name))
			throw new InvalidRefNameException(MessageFormat.format(JGitText
					.get().branchNameInvalid, name == null ? "<null>" : name)); //$NON-NLS-1$
	}

	/**
	 * Check if the given branch name is valid
	 *
	 * @param branchName
	 *            branch name to check
	 * @return {@code true} if the branch name is valid
	 *
	 * @since 5.0
	 */
	public static boolean isValidBranchName(String branchName) {
		if (HEAD.equals(branchName)) {
			return false;
		}
		return !branchName.startsWith("-"); //$NON-NLS-1$
	}

	/**
	 * Set the name of the new branch
	 *
	 * @param name
	 *            the name of the new branch
	 * @return this instance
	 */
	public CreateBranchCommand setName(String name) {
		checkCallable();
		this.name = name;
		return this;
	}

	/**
	 * Set whether to create the branch forcefully
	 *
	 * @param force
	 *            if <code>true</code> and the branch with the given name
	 *            already exists, the start-point of an existing branch will be
	 *            set to a new start-point; if false, the existing branch will
	 *            not be changed
	 * @return this instance
	 */
	public CreateBranchCommand setForce(boolean force) {
		checkCallable();
		this.force = force;
		return this;
	}

	/**
	 * Set the start point
	 *
	 * @param startPoint
	 *            corresponds to the start-point option; if <code>null</code>,
	 *            the current HEAD will be used
	 * @return this instance
	 */
	public CreateBranchCommand setStartPoint(String startPoint) {
		checkCallable();
		this.startPoint = startPoint;
		this.startCommit = null;
		return this;
	}

	/**
	 * Set the start point
	 *
	 * @param startPoint
	 *            corresponds to the start-point option; if <code>null</code>,
	 *            the current HEAD will be used
	 * @return this instance
	 */
	public CreateBranchCommand setStartPoint(RevCommit startPoint) {
		checkCallable();
		this.startCommit = startPoint;
		this.startPoint = null;
		return this;
	}

	/**
	 * Set the upstream mode
	 *
	 * @param mode
	 *            corresponds to the --track/--no-track/--set-upstream options;
	 *            may be <code>null</code>
	 * @return this instance
	 */
	public CreateBranchCommand setUpstreamMode(SetupUpstreamMode mode) {
		checkCallable();
		this.upstreamMode = mode;
		return this;
	}
}
