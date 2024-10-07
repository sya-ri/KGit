/*
 * Copyright (C) 2013, Google Inc. and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FIFORevQueue;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Command to find human-readable names of revisions.
 *
 * @see <a
 *      href="http://www.kernel.org/pub/software/scm/git/docs/git-name-rev.html"
 *      >Git documentation about name-rev</a>
 * @since 3.0
 */
public class NameRevCommand extends GitCommand<Map<ObjectId, String>> {
	/** Amount of slop to allow walking past the earliest requested commit. */
	private static final int COMMIT_TIME_SLOP = 60 * 60 * 24;

	/** Cost of traversing a merge commit compared to a linear history. */
	private static final int MERGE_COST = 65535;

	private static class NameRevCommit extends RevCommit {
		private String tip;
		private int distance;
		private long cost;

		private NameRevCommit(AnyObjectId id) {
			super(id);
		}

		private StringBuilder format() {
			StringBuilder sb = new StringBuilder(tip);
			if (distance > 0)
				sb.append('~').append(distance);
			return sb;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(getClass().getSimpleName())
				.append('[');
			if (tip != null) {
				sb.append(format());
			} else {
				sb.append((Object) null);
			}
			sb.append(',').append(cost).append(']').append(' ')
					.append(super.toString());
			return sb.toString();
		}
	}

	private final RevWalk walk;
	private final List<String> prefixes;
	private final List<ObjectId> revs;
	private List<Ref> refs;
	private int mergeCost;

	/**
	 * Create a new name-rev command.
	 *
	 * @param repo
	 *            the {@link org.eclipse.jgit.lib.Repository}
	 */
	protected NameRevCommand(Repository repo) {
		super(repo);
		mergeCost = MERGE_COST;
		prefixes = new ArrayList<>(2);
		revs = new ArrayList<>(2);
		walk = new RevWalk(repo) {
			@Override
			public NameRevCommit createCommit(AnyObjectId id) {
				return new NameRevCommit(id);
			}
		};
	}

	@Override
	public Map<ObjectId, String> call() throws GitAPIException {
		try {
			Map<ObjectId, String> nonCommits = new HashMap<>();
			FIFORevQueue pending = new FIFORevQueue();
			if (refs != null) {
				for (Ref ref : refs)
					addRef(ref, nonCommits, pending);
			}
			addPrefixes(nonCommits, pending);
			int cutoff = minCommitTime() - COMMIT_TIME_SLOP;

			while (true) {
				NameRevCommit c = (NameRevCommit) pending.next();
				if (c == null)
					break;
				if (c.getCommitTime() < cutoff)
					continue;
				for (int i = 0; i < c.getParentCount(); i++) {
					NameRevCommit p = (NameRevCommit) walk.parseCommit(c.getParent(i));
					long cost = c.cost + (i > 0 ? mergeCost : 1);
					if (p.tip == null || compare(c.tip, cost, p.tip, p.cost) < 0) {
						if (i > 0) {
							p.tip = c.format().append('^').append(i + 1).toString();
							p.distance = 0;
						} else {
							p.tip = c.tip;
							p.distance = c.distance + 1;
						}
						p.cost = cost;
						pending.add(p);
					}
				}
			}

			Map<ObjectId, String> result =
				new LinkedHashMap<>(revs.size());
			for (ObjectId id : revs) {
				RevObject o = walk.parseAny(id);
				if (o instanceof NameRevCommit) {
					NameRevCommit c = (NameRevCommit) o;
					if (c.tip != null)
						result.put(id, simplify(c.format().toString()));
				} else {
					String name = nonCommits.get(id);
					if (name != null)
						result.put(id, simplify(name));
				}
			}

			setCallable(false);
			return result;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} finally {
			walk.close();
		}
	}

	/**
	 * Add an object to search for.
	 *
	 * @param id
	 *            object ID to add.
	 * @return {@code this}
	 * @throws org.eclipse.jgit.errors.MissingObjectException
	 *             the object supplied is not available from the object
	 *             database.
	 * @throws org.eclipse.jgit.api.errors.JGitInternalException
	 *             a low-level exception of JGit has occurred. The original
	 *             exception can be retrieved by calling
	 *             {@link java.lang.Exception#getCause()}.
	 */
	public NameRevCommand add(ObjectId id) throws MissingObjectException,
			JGitInternalException {
		checkCallable();
		try {
			walk.parseAny(id);
		} catch (MissingObjectException e) {
			throw e;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage(), e);
		}
		revs.add(id.copy());
		return this;
	}

	/**
	 * Add multiple objects to search for.
	 *
	 * @param ids
	 *            object IDs to add.
	 * @return {@code this}
	 * @throws org.eclipse.jgit.errors.MissingObjectException
	 *             the object supplied is not available from the object
	 *             database.
	 * @throws org.eclipse.jgit.api.errors.JGitInternalException
	 *             a low-level exception of JGit has occurred. The original
	 *             exception can be retrieved by calling
	 *             {@link java.lang.Exception#getCause()}.
	 */
	public NameRevCommand add(Iterable<ObjectId> ids)
			throws MissingObjectException, JGitInternalException {
		for (ObjectId id : ids)
			add(id);
		return this;
	}

	/**
	 * Add a ref prefix to the set that results must match.
	 * <p>
	 * If an object matches multiple refs equally well, the first matching ref
	 * added with {@link #addRef(Ref)} is preferred, or else the first matching
	 * prefix added by {@link #addPrefix(String)}.
	 *
	 * @param prefix
	 *            prefix to add; the prefix must end with a slash
	 * @return {@code this}
	 */
	public NameRevCommand addPrefix(String prefix) {
		checkCallable();
		prefixes.add(prefix);
		return this;
	}

	/**
	 * Add all annotated tags under {@code refs/tags/} to the set that all
	 * results must match.
	 * <p>
	 * Calls {@link #addRef(Ref)}; see that method for a note on matching
	 * priority.
	 *
	 * @return {@code this}
	 * @throws JGitInternalException
	 *             a low-level exception of JGit has occurred. The original
	 *             exception can be retrieved by calling
	 *             {@link java.lang.Exception#getCause()}.
	 */
	public NameRevCommand addAnnotatedTags() {
		checkCallable();
		if (refs == null)
			refs = new ArrayList<>();
		try {
			for (Ref ref : repo.getRefDatabase()
					.getRefsByPrefix(Constants.R_TAGS)) {
				ObjectId id = ref.getObjectId();
				if (id != null && (walk.parseAny(id) instanceof RevTag))
					addRef(ref);
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage(), e);
		}
		return this;
	}

	/**
	 * Add a ref to the set that all results must match.
	 * <p>
	 * If an object matches multiple refs equally well, the first matching ref
	 * added with {@link #addRef(Ref)} is preferred, or else the first matching
	 * prefix added by {@link #addPrefix(String)}.
	 *
	 * @param ref
	 *            ref to add.
	 * @return {@code this}
	 */
	public NameRevCommand addRef(Ref ref) {
		checkCallable();
		if (refs == null)
			refs = new ArrayList<>();
		refs.add(ref);
		return this;
	}

	NameRevCommand setMergeCost(int cost) {
		mergeCost = cost;
		return this;
	}

	private void addPrefixes(Map<ObjectId, String> nonCommits,
			FIFORevQueue pending) throws IOException {
		if (!prefixes.isEmpty()) {
			for (String prefix : prefixes)
				addPrefix(prefix, nonCommits, pending);
		} else if (refs == null)
			addPrefix(Constants.R_REFS, nonCommits, pending);
	}

	private void addPrefix(String prefix, Map<ObjectId, String> nonCommits,
			FIFORevQueue pending) throws IOException {
		for (Ref ref : repo.getRefDatabase().getRefsByPrefix(prefix))
			addRef(ref, nonCommits, pending);
	}

	private void addRef(Ref ref, Map<ObjectId, String> nonCommits,
			FIFORevQueue pending) throws IOException {
		if (ref.getObjectId() == null)
			return;
		RevObject o = walk.parseAny(ref.getObjectId());
		while (o instanceof RevTag) {
			RevTag t = (RevTag) o;
			nonCommits.put(o, ref.getName());
			o = t.getObject();
			walk.parseHeaders(o);
		}
		if (o instanceof NameRevCommit) {
			NameRevCommit c = (NameRevCommit) o;
			if (c.tip == null)
				c.tip = ref.getName();
			pending.add(c);
		} else if (!nonCommits.containsKey(o))
			nonCommits.put(o, ref.getName());
	}

	private int minCommitTime() throws IOException {
		int min = Integer.MAX_VALUE;
		for (ObjectId id : revs) {
			RevObject o = walk.parseAny(id);
			while (o instanceof RevTag) {
				o = ((RevTag) o).getObject();
				walk.parseHeaders(o);
			}
			if (o instanceof RevCommit) {
				RevCommit c = (RevCommit) o;
				if (c.getCommitTime() < min)
					min = c.getCommitTime();
			}
		}
		return min;
	}

	private long compare(String leftTip, long leftCost, String rightTip, long rightCost) {
		long c = leftCost - rightCost;
		if (c != 0 || prefixes.isEmpty())
			return c;
		int li = -1;
		int ri = -1;
		for (int i = 0; i < prefixes.size(); i++) {
			String prefix = prefixes.get(i);
			if (li < 0 && leftTip.startsWith(prefix))
				li = i;
			if (ri < 0 && rightTip.startsWith(prefix))
				ri = i;
		}
		// Don't tiebreak if prefixes are the same, in order to prefer first-parent
		// paths.
		return li - ri;
	}

	private static String simplify(String refName) {
		if (refName.startsWith(Constants.R_HEADS))
			return refName.substring(Constants.R_HEADS.length());
		if (refName.startsWith(Constants.R_TAGS))
			return refName.substring(Constants.R_TAGS.length());
		if (refName.startsWith(Constants.R_REFS))
			return refName.substring(Constants.R_REFS.length());
		return refName;
	}
}
