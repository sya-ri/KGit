/*
 * Copyright (C) 2011, Chris Aniszczyk <zx@redhat.com>
 * Copyright (C) 2011, Abhishek Bhatnagar <abhatnag@redhat.com> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.DOT_GIT;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.Paths;

/**
 * Remove untracked files from the working tree
 *
 * @see <a
 *      href="http://www.kernel.org/pub/software/scm/git/docs/git-clean.html"
 *      >Git documentation about Clean</a>
 */
public class CleanCommand extends GitCommand<Set<String>> {

	private Set<String> paths = Collections.emptySet();

	private boolean dryRun;

	private boolean directories;

	private boolean ignore = true;

	private boolean force = false;

	/**
	 * Constructor for CleanCommand
	 *
	 * @param repo
	 *            the {@link org.eclipse.jgit.lib.Repository}
	 */
	protected CleanCommand(Repository repo) {
		super(repo);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Executes the {@code clean} command with all the options and parameters
	 * collected by the setter methods of this class. Each instance of this
	 * class should only be used for one invocation of the command (means: one
	 * call to {@link #call()})
	 */
	@Override
	public Set<String> call() throws NoWorkTreeException, GitAPIException {
		Set<String> files = new TreeSet<>();
		try {
			StatusCommand command = new StatusCommand(repo);
			Status status = command.call();

			Set<String> untrackedFiles = new TreeSet<>(status.getUntracked());
			Set<String> untrackedDirs = new TreeSet<>(
					status.getUntrackedFolders());

			FS fs = getRepository().getFS();
			for (String p : status.getIgnoredNotInIndex()) {
				File f = new File(repo.getWorkTree(), p);
				if (fs.isFile(f) || fs.isSymLink(f)) {
					untrackedFiles.add(p);
				} else if (fs.isDirectory(f)) {
					untrackedDirs.add(p);
				}
			}

			Set<String> filtered = filterFolders(untrackedFiles, untrackedDirs);

			Set<String> notIgnoredFiles = filterIgnorePaths(filtered,
					status.getIgnoredNotInIndex(), true);
			Set<String> notIgnoredDirs = filterIgnorePaths(untrackedDirs,
					status.getIgnoredNotInIndex(), false);

			for (String file : notIgnoredFiles) {
				if (paths.isEmpty() || paths.contains(file)) {
					files = cleanPath(file, files);
				}
			}
			for (String dir : notIgnoredDirs) {
				if (paths.isEmpty() || paths.contains(dir)) {
					files = cleanPath(dir, files);
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} finally {
			if (!dryRun && !files.isEmpty()) {
				repo.fireEvent(new WorkingTreeModifiedEvent(null, files));
			}
		}
		return files;
	}

	/**
	 * When dryRun is false, deletes the specified path from disk. If dryRun is
	 * true, no paths are actually deleted. In both cases, the paths that would
	 * have been deleted are added to inFiles and returned.
	 *
	 * Paths that are directories are recursively deleted when
	 * {@link #directories} is true. Paths that are git repositories are
	 * recursively deleted when {@link #directories} and {@link #force} are both
	 * true.
	 *
	 * @param path
	 *            The path to be cleaned
	 * @param inFiles
	 *            A set of strings representing the files that have been cleaned
	 *            already, the path to be cleaned will be added to this set
	 *            before being returned.
	 *
	 * @return a set of strings with the cleaned path added to it
	 * @throws IOException
	 *             if an IO error occurred
	 */
	private Set<String> cleanPath(String path, Set<String> inFiles)
			throws IOException {
		File curFile = new File(repo.getWorkTree(), path);
		if (curFile.isDirectory()) {
			if (directories) {
				// Is this directory a git repository?
				if (new File(curFile, DOT_GIT).exists()) {
					if (force) {
						if (!dryRun) {
							FileUtils.delete(curFile, FileUtils.RECURSIVE
									| FileUtils.SKIP_MISSING);
						}
						inFiles.add(path + '/');
					}
				} else {
					if (!dryRun) {
						FileUtils.delete(curFile,
								FileUtils.RECURSIVE | FileUtils.SKIP_MISSING);
					}
					inFiles.add(path + '/');
				}
			}
		} else {
			if (!dryRun) {
				FileUtils.delete(curFile, FileUtils.SKIP_MISSING);
			}
			inFiles.add(path);
		}

		return inFiles;
	}

	private Set<String> filterIgnorePaths(Set<String> inputPaths,
			Set<String> ignoredNotInIndex, boolean exact) {
		if (ignore) {
			Set<String> filtered = new TreeSet<>(inputPaths);
			for (String path : inputPaths) {
				for (String ignored : ignoredNotInIndex) {
					if ((exact && path.equals(ignored))
							|| (!exact
									&& Paths.isEqualOrPrefix(ignored, path))) {
						filtered.remove(path);
						break;
					}
				}
			}
			return filtered;
		}
		return inputPaths;
	}

	private Set<String> filterFolders(Set<String> untracked,
			Set<String> untrackedFolders) {
		Set<String> filtered = new TreeSet<>(untracked);
		for (String file : untracked) {
			for (String folder : untrackedFolders) {
				if (Paths.isEqualOrPrefix(folder, file)) {
					filtered.remove(file);
					break;
				}
			}
		}
		return filtered;
	}

	/**
	 * If paths are set, only these paths are affected by the cleaning.
	 *
	 * @param paths
	 *            the paths to set (with <code>/</code> as separator)
	 * @return {@code this}
	 */
	public CleanCommand setPaths(Set<String> paths) {
		this.paths = paths;
		return this;
	}

	/**
	 * If dryRun is set, the paths in question will not actually be deleted.
	 *
	 * @param dryRun
	 *            whether to do a dry run or not
	 * @return {@code this}
	 */
	public CleanCommand setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
		return this;
	}

	/**
	 * If force is set, directories that are git repositories will also be
	 * deleted.
	 *
	 * @param force
	 *            whether or not to delete git repositories
	 * @return {@code this}
	 * @since 4.5
	 */
	public CleanCommand setForce(boolean force) {
		this.force = force;
		return this;
	}

	/**
	 * If dirs is set, in addition to files, also clean directories.
	 *
	 * @param dirs
	 *            whether to clean directories too, or only files.
	 * @return {@code this}
	 */
	public CleanCommand setCleanDirectories(boolean dirs) {
		directories = dirs;
		return this;
	}

	/**
	 * If ignore is set, don't report/clean files/directories that are ignored
	 * by a .gitignore. otherwise do handle them.
	 *
	 * @param ignore
	 *            whether to respect .gitignore or not.
	 * @return {@code this}
	 */
	public CleanCommand setIgnore(boolean ignore) {
		this.ignore = ignore;
		return this;
	}
}
