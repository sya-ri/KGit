/*
 * Copyright (C) 2010, Stefan Lay <stefan.lay@sap.com>
 * Copyright (C) 2010-2012, Christian Halstrick <christian.halstrick@sap.com> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.merge.MergeChunk;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;

/**
 * Encapsulates the result of a {@link org.eclipse.jgit.api.MergeCommand}.
 */
public class MergeResult {

	/**
	 * The status the merge resulted in.
	 */
	public enum MergeStatus {
		/** */
		FAST_FORWARD {
			@Override
			public String toString() {
				return "Fast-forward"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		},
		/**
		 * @since 2.0
		 */
		FAST_FORWARD_SQUASHED {
			@Override
			public String toString() {
				return "Fast-forward-squashed"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		},
		/** */
		ALREADY_UP_TO_DATE {
			@Override
			public String toString() {
				return "Already-up-to-date"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		},
		/** */
		FAILED {
			@Override
			public String toString() {
				return "Failed"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		},
		/** */
		MERGED {
			@Override
			public String toString() {
				return "Merged"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		},
		/**
		 * @since 2.0
		 */
		MERGED_SQUASHED {
			@Override
			public String toString() {
				return "Merged-squashed"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		},
		/**
		 * @since 3.0
		 */
		MERGED_SQUASHED_NOT_COMMITTED {
			@Override
			public String toString() {
				return "Merged-squashed-not-committed"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		},
		/** */
		CONFLICTING {
			@Override
			public String toString() {
				return "Conflicting"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		},
		/**
		 * @since 2.2
		 */
		ABORTED {
			@Override
			public String toString() {
				return "Aborted"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		},
		/**
		 * @since 3.0
		 **/
		MERGED_NOT_COMMITTED {
			@Override
			public String toString() {
				return "Merged-not-committed"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		},
		/** */
		NOT_SUPPORTED {
			@Override
			public String toString() {
				return "Not-yet-supported"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		},
		/**
		 * Status representing a checkout conflict, meaning that nothing could
		 * be merged, as the pre-scan for the trees already failed for certain
		 * files (i.e. local modifications prevent checkout of files).
		 */
		CHECKOUT_CONFLICT {
			@Override
			public String toString() {
				return "Checkout Conflict"; //$NON-NLS-1$
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		};

		/**
		 * @return whether the status indicates a successful result
		 */
		public abstract boolean isSuccessful();
	}

	private ObjectId[] mergedCommits;

	private ObjectId base;

	private ObjectId newHead;

	private Map<String, int[][]> conflicts;

	private MergeStatus mergeStatus;

	private String description;

	private MergeStrategy mergeStrategy;

	private Map<String, MergeFailureReason> failingPaths;

	private List<String> checkoutConflicts;

	/**
	 * Constructor for MergeResult.
	 *
	 * @param newHead
	 *            the object the head points at after the merge
	 * @param base
	 *            the common base which was used to produce a content-merge. May
	 *            be <code>null</code> if the merge-result was produced without
	 *            computing a common base
	 * @param mergedCommits
	 *            all the commits which have been merged together
	 * @param mergeStatus
	 *            the status the merge resulted in
	 * @param mergeStrategy
	 *            the used {@link org.eclipse.jgit.merge.MergeStrategy}
	 * @param lowLevelResults
	 *            merge results as returned by
	 *            {@link org.eclipse.jgit.merge.ResolveMerger#getMergeResults()}
	 * @since 2.0
	 */
	public MergeResult(ObjectId newHead, ObjectId base,
			ObjectId[] mergedCommits, MergeStatus mergeStatus,
			MergeStrategy mergeStrategy,
			Map<String, org.eclipse.jgit.merge.MergeResult<?>> lowLevelResults) {
		this(newHead, base, mergedCommits, mergeStatus, mergeStrategy,
				lowLevelResults, null);
	}

	/**
	 * Constructor for MergeResult.
	 *
	 * @param newHead
	 *            the object the head points at after the merge
	 * @param base
	 *            the common base which was used to produce a content-merge. May
	 *            be <code>null</code> if the merge-result was produced without
	 *            computing a common base
	 * @param mergedCommits
	 *            all the commits which have been merged together
	 * @param mergeStatus
	 *            the status the merge resulted in
	 * @param mergeStrategy
	 *            the used {@link org.eclipse.jgit.merge.MergeStrategy}
	 * @param lowLevelResults
	 *            merge results as returned by
	 *            {@link org.eclipse.jgit.merge.ResolveMerger#getMergeResults()}
	 * @param description
	 *            a user friendly description of the merge result
	 */
	public MergeResult(ObjectId newHead, ObjectId base,
			ObjectId[] mergedCommits, MergeStatus mergeStatus,
			MergeStrategy mergeStrategy,
			Map<String, org.eclipse.jgit.merge.MergeResult<?>> lowLevelResults,
			String description) {
		this(newHead, base, mergedCommits, mergeStatus, mergeStrategy,
				lowLevelResults, null, description);
	}

	/**
	 * Constructor for MergeResult.
	 *
	 * @param newHead
	 *            the object the head points at after the merge
	 * @param base
	 *            the common base which was used to produce a content-merge. May
	 *            be <code>null</code> if the merge-result was produced without
	 *            computing a common base
	 * @param mergedCommits
	 *            all the commits which have been merged together
	 * @param mergeStatus
	 *            the status the merge resulted in
	 * @param mergeStrategy
	 *            the used {@link org.eclipse.jgit.merge.MergeStrategy}
	 * @param lowLevelResults
	 *            merge results as returned by
	 *            {@link org.eclipse.jgit.merge.ResolveMerger#getMergeResults()}
	 * @param failingPaths
	 *            list of paths causing this merge to fail as returned by
	 *            {@link org.eclipse.jgit.merge.ResolveMerger#getFailingPaths()}
	 * @param description
	 *            a user friendly description of the merge result
	 */
	public MergeResult(ObjectId newHead, ObjectId base,
			ObjectId[] mergedCommits, MergeStatus mergeStatus,
			MergeStrategy mergeStrategy,
			Map<String, org.eclipse.jgit.merge.MergeResult<?>> lowLevelResults,
			Map<String, MergeFailureReason> failingPaths, String description) {
		this.newHead = newHead;
		this.mergedCommits = mergedCommits;
		this.base = base;
		this.mergeStatus = mergeStatus;
		this.mergeStrategy = mergeStrategy;
		this.description = description;
		this.failingPaths = failingPaths;
		if (lowLevelResults != null)
			for (Map.Entry<String, org.eclipse.jgit.merge.MergeResult<?>> result : lowLevelResults
					.entrySet())
				addConflict(result.getKey(), result.getValue());
	}

	/**
	 * Creates a new result that represents a checkout conflict before the
	 * operation even started for real.
	 *
	 * @param checkoutConflicts
	 *            the conflicting files
	 */
	public MergeResult(List<String> checkoutConflicts) {
		this.checkoutConflicts = checkoutConflicts;
		this.mergeStatus = MergeStatus.CHECKOUT_CONFLICT;
	}

	/**
	 * Get the object the head points at after the merge
	 *
	 * @return the object the head points at after the merge
	 */
	public ObjectId getNewHead() {
		return newHead;
	}

	/**
	 * Get the merge status
	 *
	 * @return the status the merge resulted in
	 */
	public MergeStatus getMergeStatus() {
		return mergeStatus;
	}

	/**
	 * Get the commits which have been merged
	 *
	 * @return all the commits which have been merged together
	 */
	public ObjectId[] getMergedCommits() {
		return mergedCommits;
	}

	/**
	 * Get the common base
	 *
	 * @return base the common base which was used to produce a content-merge.
	 *         May be <code>null</code> if the merge-result was produced without
	 *         computing a common base
	 */
	public ObjectId getBase() {
		return base;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		boolean first = true;
		StringBuilder commits = new StringBuilder();
		for (ObjectId commit : mergedCommits) {
			if (!first)
				commits.append(", ");
			else
				first = false;
			commits.append(ObjectId.toString(commit));
		}
		return MessageFormat.format(
				JGitText.get().mergeUsingStrategyResultedInDescription,
				commits, ObjectId.toString(base), mergeStrategy.getName(),
				mergeStatus, (description == null ? "" : ", " + description));
	}

	/**
	 * Set conflicts
	 *
	 * @param conflicts
	 *            the conflicts to set
	 */
	public void setConflicts(Map<String, int[][]> conflicts) {
		this.conflicts = conflicts;
	}

	/**
	 * Add a conflict
	 *
	 * @param path
	 *            path of the file to add a conflict for
	 * @param conflictingRanges
	 *            the conflicts to set
	 */
	public void addConflict(String path, int[][] conflictingRanges) {
		if (conflicts == null)
			conflicts = new HashMap<>();
		conflicts.put(path, conflictingRanges);
	}

	/**
	 * Add a conflict
	 *
	 * @param path
	 *            path of the file to add a conflict for
	 * @param lowLevelResult
	 *            a {@link org.eclipse.jgit.merge.MergeResult}
	 */
	public void addConflict(String path, org.eclipse.jgit.merge.MergeResult<?> lowLevelResult) {
		if (!lowLevelResult.containsConflicts())
			return;
		if (conflicts == null)
			conflicts = new HashMap<>();
		int nrOfConflicts = 0;
		// just counting
		for (MergeChunk mergeChunk : lowLevelResult) {
			if (mergeChunk.getConflictState().equals(ConflictState.FIRST_CONFLICTING_RANGE)) {
				nrOfConflicts++;
			}
		}
		int currentConflict = -1;
		int[][] ret=new int[nrOfConflicts][mergedCommits.length+1];
		for (MergeChunk mergeChunk : lowLevelResult) {
			// to store the end of this chunk (end of the last conflicting range)
			int endOfChunk = 0;
			if (mergeChunk.getConflictState().equals(ConflictState.FIRST_CONFLICTING_RANGE)) {
				if (currentConflict > -1) {
					// there was a previous conflicting range for which the end
					// is not set yet - set it!
					ret[currentConflict][mergedCommits.length] = endOfChunk;
				}
				currentConflict++;
				endOfChunk = mergeChunk.getEnd();
				ret[currentConflict][mergeChunk.getSequenceIndex()] = mergeChunk.getBegin();
			}
			if (mergeChunk.getConflictState().equals(ConflictState.NEXT_CONFLICTING_RANGE)) {
				if (mergeChunk.getEnd() > endOfChunk)
					endOfChunk = mergeChunk.getEnd();
				ret[currentConflict][mergeChunk.getSequenceIndex()] = mergeChunk.getBegin();
			}
		}
		conflicts.put(path, ret);
	}

	/**
	 * Returns information about the conflicts which occurred during a
	 * {@link org.eclipse.jgit.api.MergeCommand}. The returned value maps the
	 * path of a conflicting file to a two-dimensional int-array of line-numbers
	 * telling where in the file conflict markers for which merged commit can be
	 * found.
	 * <p>
	 * If the returned value contains a mapping "path"-&gt;[x][y]=z then this
	 * means
	 * <ul>
	 * <li>the file with path "path" contains conflicts</li>
	 * <li>if y &lt; "number of merged commits": for conflict number x in this
	 * file the chunk which was copied from commit number y starts on line
	 * number z. All numberings and line numbers start with 0.</li>
	 * <li>if y == "number of merged commits": the first non-conflicting line
	 * after conflict number x starts at line number z</li>
	 * </ul>
	 * <p>
	 * Example code how to parse this data:
	 *
	 * <pre>
	 * MergeResult m=...;
	 * Map&lt;String, int[][]&gt; allConflicts = m.getConflicts();
	 * for (String path : allConflicts.keySet()) {
	 * 	int[][] c = allConflicts.get(path);
	 * 	System.out.println("Conflicts in file " + path);
	 * 	for (int i = 0; i &lt; c.length; ++i) {
	 * 		System.out.println("  Conflict #" + i);
	 * 		for (int j = 0; j &lt; (c[i].length) - 1; ++j) {
	 * 			if (c[i][j] &gt;= 0)
	 * 				System.out.println("    Chunk for "
	 * 						+ m.getMergedCommits()[j] + " starts on line #"
	 * 						+ c[i][j]);
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 *
	 * @return the conflicts or <code>null</code> if no conflict occurred
	 */
	public Map<String, int[][]> getConflicts() {
		return conflicts;
	}

	/**
	 * Returns a list of paths causing this merge to fail as returned by
	 * {@link org.eclipse.jgit.merge.ResolveMerger#getFailingPaths()}
	 *
	 * @return the list of paths causing this merge to fail or <code>null</code>
	 *         if no failure occurred
	 */
	public Map<String, MergeFailureReason> getFailingPaths() {
		return failingPaths;
	}

	/**
	 * Returns a list of paths that cause a checkout conflict. These paths
	 * prevent the operation from even starting.
	 *
	 * @return the list of files that caused the checkout conflict.
	 */
	public List<String> getCheckoutConflicts() {
		return checkoutConflicts;
	}
}
