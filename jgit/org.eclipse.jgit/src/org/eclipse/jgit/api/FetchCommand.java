/*
 * Copyright (C) 2010, 2022 Chris Aniszczyk <caniszczyk@gmail.com> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.api;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.SubmoduleConfig.FetchRecurseSubmodulesMode;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.TagOpt;
import org.eclipse.jgit.transport.Transport;

/**
 * A class used to execute a {@code Fetch} command. It has setters for all
 * supported options and arguments of this command and a {@link #call()} method
 * to finally execute the command.
 *
 * @see <a href="http://www.kernel.org/pub/software/scm/git/docs/git-fetch.html"
 *      >Git documentation about Fetch</a>
 */
public class FetchCommand extends TransportCommand<FetchCommand, FetchResult> {
	private String remote = Constants.DEFAULT_REMOTE_NAME;

	private List<RefSpec> refSpecs;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private boolean checkFetchedObjects;

	private Boolean removeDeletedRefs;

	private boolean dryRun;

	private boolean thin = Transport.DEFAULT_FETCH_THIN;

	private TagOpt tagOption;

	private FetchRecurseSubmodulesMode submoduleRecurseMode = null;

	private Callback callback;

	private boolean isForceUpdate;

	private String initialBranch;

	private Integer depth;

	private Instant deepenSince;

	private List<String> shallowExcludes = new ArrayList<>();

	private boolean unshallow;

	/**
	 * Callback for status of fetch operation.
	 *
	 * @since 4.8
	 *
	 */
	public interface Callback {
		/**
		 * Notify fetching a submodule.
		 *
		 * @param name
		 *            the submodule name.
		 */
		void fetchingSubmodule(String name);
	}

	/**
	 * Constructor for FetchCommand.
	 *
	 * @param repo
	 *            a {@link org.eclipse.jgit.lib.Repository} object.
	 */
	protected FetchCommand(Repository repo) {
		super(repo);
		refSpecs = new ArrayList<>(3);
	}

	private FetchRecurseSubmodulesMode getRecurseMode(String path) {
		// Use the caller-specified mode, if set
		if (submoduleRecurseMode != null) {
			return submoduleRecurseMode;
		}

		// Fall back to submodule.name.fetchRecurseSubmodules, if set
		FetchRecurseSubmodulesMode mode = repo.getConfig().getEnum(
				FetchRecurseSubmodulesMode.values(),
				ConfigConstants.CONFIG_SUBMODULE_SECTION, path,
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES, null);
		if (mode != null) {
			return mode;
		}

		// Fall back to fetch.recurseSubmodules, if set
		mode = repo.getConfig().getEnum(FetchRecurseSubmodulesMode.values(),
				ConfigConstants.CONFIG_FETCH_SECTION, null,
				ConfigConstants.CONFIG_KEY_RECURSE_SUBMODULES, null);
		if (mode != null) {
			return mode;
		}

		// Default to on-demand mode
		return FetchRecurseSubmodulesMode.ON_DEMAND;
	}

	private void fetchSubmodules(FetchResult results)
			throws org.eclipse.jgit.api.errors.TransportException,
			GitAPIException, InvalidConfigurationException {
		try (SubmoduleWalk walk = new SubmoduleWalk(repo);
				RevWalk revWalk = new RevWalk(repo)) {
			// Walk over submodules in the parent repository's FETCH_HEAD.
			ObjectId fetchHead = repo.resolve(Constants.FETCH_HEAD);
			if (fetchHead == null) {
				return;
			}
			if (revWalk.parseAny(fetchHead).getType() == Constants.OBJ_BLOB) {
				return;
			}
			walk.setTree(revWalk.parseTree(fetchHead));
			while (walk.next()) {
				try (Repository submoduleRepo = walk.getRepository()) {

					// Skip submodules that don't exist locally (have not been
					// cloned), are not registered in the .gitmodules file, or
					// not registered in the parent repository's config.
					if (submoduleRepo == null || walk.getModulesPath() == null
							|| walk.getConfigUrl() == null) {
						continue;
					}

					FetchRecurseSubmodulesMode recurseMode = getRecurseMode(
							walk.getPath());

					// When the fetch mode is "yes" we always fetch. When the
					// mode is "on demand", we only fetch if the submodule's
					// revision was updated to an object that is not currently
					// present in the submodule.
					if ((recurseMode == FetchRecurseSubmodulesMode.ON_DEMAND
							&& !submoduleRepo.getObjectDatabase()
									.has(walk.getObjectId()))
							|| recurseMode == FetchRecurseSubmodulesMode.YES) {
						FetchCommand f = new FetchCommand(submoduleRepo)
								.setProgressMonitor(monitor)
								.setTagOpt(tagOption)
								.setCheckFetchedObjects(checkFetchedObjects)
								.setRemoveDeletedRefs(isRemoveDeletedRefs())
								.setThin(thin)
								.setRefSpecs(applyOptions(refSpecs))
								.setDryRun(dryRun)
								.setRecurseSubmodules(recurseMode);
						configure(f);
						if (callback != null) {
							callback.fetchingSubmodule(walk.getPath());
						}
						results.addSubmodule(walk.getPath(), f.call());
					}
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (ConfigInvalidException e) {
			throw new InvalidConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Execute the {@code fetch} command with all the options and parameters
	 * collected by the setter methods of this class. Each instance of this
	 * class should only be used for one invocation of the command (means: one
	 * call to {@link #call()})
	 */
	@Override
	public FetchResult call() throws GitAPIException, InvalidRemoteException,
			org.eclipse.jgit.api.errors.TransportException {
		checkCallable();

		try (Transport transport = Transport.open(repo, remote)) {
			transport.setCheckFetchedObjects(checkFetchedObjects);
			transport.setRemoveDeletedRefs(isRemoveDeletedRefs());
			transport.setDryRun(dryRun);
			if (tagOption != null)
				transport.setTagOpt(tagOption);
			transport.setFetchThin(thin);
			if (depth != null) {
				transport.setDepth(depth);
			}
			if (unshallow) {
				if (depth != null) {
					throw new IllegalStateException(JGitText.get().depthWithUnshallow);
				}
				transport.setDepth(Constants.INFINITE_DEPTH);
			}
			if (deepenSince != null) {
				transport.setDeepenSince(deepenSince);
			}
			transport.setDeepenNots(shallowExcludes);
			configure(transport);
			FetchResult result = transport.fetch(monitor,
					applyOptions(refSpecs), initialBranch);
			if (!repo.isBare()) {
				fetchSubmodules(result);
			}

			return result;
		} catch (NoRemoteRepositoryException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote, remote), e);
		} catch (TransportException e) {
			throw new org.eclipse.jgit.api.errors.TransportException(
					e.getMessage(), e);
		} catch (URISyntaxException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote, remote), e);
		} catch (NotSupportedException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfFetchCommand,
					e);
		}

	}

	private List<RefSpec> applyOptions(List<RefSpec> refSpecs2) {
		if (!isForceUpdate()) {
			return refSpecs2;
		}
		List<RefSpec> updated = new ArrayList<>(3);
		for (RefSpec refSpec : refSpecs2) {
			updated.add(refSpec.setForceUpdate(true));
		}
		return updated;
	}

	/**
	 * Set the mode to be used for recursing into submodules.
	 *
	 * @param recurse
	 *            corresponds to the
	 *            --recurse-submodules/--no-recurse-submodules options. If
	 *            {@code null} use the value of the
	 *            {@code submodule.name.fetchRecurseSubmodules} option
	 *            configured per submodule. If not specified there, use the
	 *            value of the {@code fetch.recurseSubmodules} option configured
	 *            in git config. If not configured in either, "on-demand" is the
	 *            built-in default.
	 * @return {@code this}
	 * @since 4.7
	 */
	public FetchCommand setRecurseSubmodules(
			@Nullable FetchRecurseSubmodulesMode recurse) {
		checkCallable();
		submoduleRecurseMode = recurse;
		return this;
	}

	/**
	 * The remote (uri or name) used for the fetch operation. If no remote is
	 * set, the default value of <code>Constants.DEFAULT_REMOTE_NAME</code> will
	 * be used.
	 *
	 * @see Constants#DEFAULT_REMOTE_NAME
	 * @param remote
	 *            name of a remote
	 * @return {@code this}
	 */
	public FetchCommand setRemote(String remote) {
		checkCallable();
		this.remote = remote;
		return this;
	}

	/**
	 * Get the remote
	 *
	 * @return the remote used for the remote operation
	 */
	public String getRemote() {
		return remote;
	}

	/**
	 * Get timeout
	 *
	 * @return the timeout used for the fetch operation
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Whether to check received objects for validity
	 *
	 * @return whether to check received objects for validity
	 */
	public boolean isCheckFetchedObjects() {
		return checkFetchedObjects;
	}

	/**
	 * If set to {@code true}, objects received will be checked for validity
	 *
	 * @param checkFetchedObjects
	 *            whether to check objects for validity
	 * @return {@code this}
	 */
	public FetchCommand setCheckFetchedObjects(boolean checkFetchedObjects) {
		checkCallable();
		this.checkFetchedObjects = checkFetchedObjects;
		return this;
	}

	/**
	 * Whether to remove refs which no longer exist in the source
	 *
	 * @return whether to remove refs which no longer exist in the source
	 */
	public boolean isRemoveDeletedRefs() {
		if (removeDeletedRefs != null) {
			return removeDeletedRefs.booleanValue();
		}
		// fall back to configuration
		boolean result = false;
		StoredConfig config = repo.getConfig();
		result = config.getBoolean(ConfigConstants.CONFIG_FETCH_SECTION, null,
				ConfigConstants.CONFIG_KEY_PRUNE, result);
		result = config.getBoolean(ConfigConstants.CONFIG_REMOTE_SECTION,
				remote, ConfigConstants.CONFIG_KEY_PRUNE, result);
		return result;
	}

	/**
	 * If set to {@code true}, refs are removed which no longer exist in the
	 * source
	 *
	 * @param removeDeletedRefs
	 *            whether to remove deleted {@code Ref}s
	 * @return {@code this}
	 */
	public FetchCommand setRemoveDeletedRefs(boolean removeDeletedRefs) {
		checkCallable();
		this.removeDeletedRefs = Boolean.valueOf(removeDeletedRefs);
		return this;
	}

	/**
	 * Get progress monitor
	 *
	 * @return the progress monitor for the fetch operation
	 */
	public ProgressMonitor getProgressMonitor() {
		return monitor;
	}

	/**
	 * The progress monitor associated with the fetch operation. By default,
	 * this is set to <code>NullProgressMonitor</code>
	 *
	 * @see NullProgressMonitor
	 * @param monitor
	 *            a {@link org.eclipse.jgit.lib.ProgressMonitor}
	 * @return {@code this}
	 */
	public FetchCommand setProgressMonitor(ProgressMonitor monitor) {
		checkCallable();
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}

	/**
	 * Get list of {@code RefSpec}s
	 *
	 * @return the ref specs
	 */
	public List<RefSpec> getRefSpecs() {
		return refSpecs;
	}

	/**
	 * The ref specs to be used in the fetch operation
	 *
	 * @param specs
	 *            String representation of {@code RefSpec}s
	 * @return {@code this}
	 * @since 4.9
	 */
	public FetchCommand setRefSpecs(String... specs) {
		return setRefSpecs(
				Arrays.stream(specs).map(RefSpec::new).collect(toList()));
	}

	/**
	 * The ref specs to be used in the fetch operation
	 *
	 * @param specs
	 *            one or multiple {@link org.eclipse.jgit.transport.RefSpec}s
	 * @return {@code this}
	 */
	public FetchCommand setRefSpecs(RefSpec... specs) {
		return setRefSpecs(Arrays.asList(specs));
	}

	/**
	 * The ref specs to be used in the fetch operation
	 *
	 * @param specs
	 *            list of {@link org.eclipse.jgit.transport.RefSpec}s
	 * @return {@code this}
	 */
	public FetchCommand setRefSpecs(List<RefSpec> specs) {
		checkCallable();
		this.refSpecs.clear();
		this.refSpecs.addAll(specs);
		return this;
	}

	/**
	 * Whether to do a dry run
	 *
	 * @return the dry run preference for the fetch operation
	 */
	public boolean isDryRun() {
		return dryRun;
	}

	/**
	 * Sets whether the fetch operation should be a dry run
	 *
	 * @param dryRun
	 *            whether to do a dry run
	 * @return {@code this}
	 */
	public FetchCommand setDryRun(boolean dryRun) {
		checkCallable();
		this.dryRun = dryRun;
		return this;
	}

	/**
	 * Get thin-pack preference
	 *
	 * @return the thin-pack preference for fetch operation
	 */
	public boolean isThin() {
		return thin;
	}

	/**
	 * Sets the thin-pack preference for fetch operation.
	 *
	 * Default setting is Transport.DEFAULT_FETCH_THIN
	 *
	 * @param thin
	 *            the thin-pack preference
	 * @return {@code this}
	 */
	public FetchCommand setThin(boolean thin) {
		checkCallable();
		this.thin = thin;
		return this;
	}

	/**
	 * Sets the specification of annotated tag behavior during fetch
	 *
	 * @param tagOpt
	 *            the {@link org.eclipse.jgit.transport.TagOpt}
	 * @return {@code this}
	 */
	public FetchCommand setTagOpt(TagOpt tagOpt) {
		checkCallable();
		this.tagOption = tagOpt;
		return this;
	}

	/**
	 * Set the initial branch
	 *
	 * @param branch
	 *            the initial branch to check out when cloning the repository.
	 *            Can be specified as ref name (<code>refs/heads/master</code>),
	 *            branch name (<code>master</code>) or tag name
	 *            (<code>v1.2.3</code>). The default is to use the branch
	 *            pointed to by the cloned repository's HEAD and can be
	 *            requested by passing {@code null} or <code>HEAD</code>.
	 * @return {@code this}
	 * @since 5.11
	 */
	public FetchCommand setInitialBranch(String branch) {
		this.initialBranch = branch;
		return this;
	}

	/**
	 * Register a progress callback.
	 *
	 * @param callback
	 *            the callback
	 * @return {@code this}
	 * @since 4.8
	 */
	public FetchCommand setCallback(Callback callback) {
		this.callback = callback;
		return this;
	}

	/**
	 * Whether fetch --force option is enabled
	 *
	 * @return whether refs affected by the fetch are updated forcefully
	 * @since 5.0
	 */
	public boolean isForceUpdate() {
		return this.isForceUpdate;
	}

	/**
	 * Set fetch --force option
	 *
	 * @param force
	 *            whether to update refs affected by the fetch forcefully
	 * @return this command
	 * @since 5.0
	 */
	public FetchCommand setForceUpdate(boolean force) {
		this.isForceUpdate = force;
		return this;
	}

	/**
	 * Limits fetching to the specified number of commits from the tip of each
	 * remote branch history.
	 *
	 * @param depth
	 *            the depth
	 * @return {@code this}
	 *
	 * @since 6.3
	 */
	public FetchCommand setDepth(int depth) {
		if (depth < 1) {
			throw new IllegalArgumentException(JGitText.get().depthMustBeAt1);
		}
		this.depth = Integer.valueOf(depth);
		return this;
	}

	/**
	 * Deepens or shortens the history of a shallow repository to include all
	 * reachable commits after a specified time.
	 *
	 * @param shallowSince
	 *            the timestammp; must not be {@code null}
	 * @return {@code this}
	 *
	 * @since 6.3
	 */
	public FetchCommand setShallowSince(@NonNull OffsetDateTime shallowSince) {
		this.deepenSince = shallowSince.toInstant();
		return this;
	}

	/**
	 * Deepens or shortens the history of a shallow repository to include all
	 * reachable commits after a specified time.
	 *
	 * @param shallowSince
	 *            the timestammp; must not be {@code null}
	 * @return {@code this}
	 *
	 * @since 6.3
	 */
	public FetchCommand setShallowSince(@NonNull Instant shallowSince) {
		this.deepenSince = shallowSince;
		return this;
	}

	/**
	 * Deepens or shortens the history of a shallow repository to exclude
	 * commits reachable from a specified remote branch or tag.
	 *
	 * @param shallowExclude
	 *            the ref or commit; must not be {@code null}
	 * @return {@code this}
	 *
	 * @since 6.3
	 */
	public FetchCommand addShallowExclude(@NonNull String shallowExclude) {
		shallowExcludes.add(shallowExclude);
		return this;
	}

	/**
	 * Creates a shallow clone with a history, excluding commits reachable from
	 * a specified remote branch or tag.
	 *
	 * @param shallowExclude
	 *            the commit; must not be {@code null}
	 * @return {@code this}
	 *
	 * @since 6.3
	 */
	public FetchCommand addShallowExclude(@NonNull ObjectId shallowExclude) {
		shallowExcludes.add(shallowExclude.name());
		return this;
	}

	/**
	 * If the source repository is complete, converts a shallow repository to a
	 * complete one, removing all the limitations imposed by shallow
	 * repositories.
	 *
	 * If the source repository is shallow, fetches as much as possible so that
	 * the current repository has the same history as the source repository.
	 *
	 * @param unshallow
	 *            whether to unshallow or not
	 * @return {@code this}
	 *
	 * @since 6.3
	 */
	public FetchCommand setUnshallow(boolean unshallow) {
		this.unshallow = unshallow;
		return this;
	}

	void setShallowExcludes(List<String> shallowExcludes) {
		this.shallowExcludes = shallowExcludes;
	}
}
