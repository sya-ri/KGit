/*
 * Copyright (C) 2012 Google Inc. and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.api;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

/**
 * Create an archive of files from a named tree.
 * <p>
 * Examples (<code>git</code> is a {@link org.eclipse.jgit.api.Git} instance):
 * <p>
 * Create a tarball from HEAD:
 *
 * <pre>
 * ArchiveCommand.registerFormat("tar", new TarFormat());
 * try {
 * 	git.archive().setTree(db.resolve(&quot;HEAD&quot;)).setOutputStream(out).call();
 * } finally {
 * 	ArchiveCommand.unregisterFormat("tar");
 * }
 * </pre>
 * <p>
 * Create a ZIP file from master:
 *
 * <pre>
 * ArchiveCommand.registerFormat("zip", new ZipFormat());
 * try {
 *	git.archive().
 *		.setTree(db.resolve(&quot;master&quot;))
 *		.setFormat("zip")
 *		.setOutputStream(out)
 *		.call();
 * } finally {
 *	ArchiveCommand.unregisterFormat("zip");
 * }
 * </pre>
 *
 * @see <a href="http://git-htmldocs.googlecode.com/git/git-archive.html" >Git
 *      documentation about archive</a>
 * @since 3.1
 */
public class ArchiveCommand extends GitCommand<OutputStream> {
	/**
	 * Archival format.
	 *
	 * Usage:
	 *	Repository repo = git.getRepository();
	 *	T out = format.createArchiveOutputStream(System.out);
	 *	try {
	 *		for (...) {
	 *			format.putEntry(out, path, mode, repo.open(objectId));
	 *		}
	 *		out.close();
	 *	}
	 *
	 * @param <T>
	 *            type representing an archive being created.
	 */
	public static interface Format<T extends Closeable> {
		/**
		 * Start a new archive. Entries can be included in the archive using the
		 * putEntry method, and then the archive should be closed using its
		 * close method.
		 *
		 * @param s
		 *            underlying output stream to which to write the archive.
		 * @return new archive object for use in putEntry
		 * @throws IOException
		 *             thrown by the underlying output stream for I/O errors
		 */
		T createArchiveOutputStream(OutputStream s) throws IOException;

		/**
		 * Start a new archive. Entries can be included in the archive using the
		 * putEntry method, and then the archive should be closed using its
		 * close method. In addition options can be applied to the underlying
		 * stream. E.g. compression level.
		 *
		 * @param s
		 *            underlying output stream to which to write the archive.
		 * @param o
		 *            options to apply to the underlying output stream. Keys are
		 *            option names and values are option values.
		 * @return new archive object for use in putEntry
		 * @throws IOException
		 *             thrown by the underlying output stream for I/O errors
		 * @since 4.0
		 */
		T createArchiveOutputStream(OutputStream s, Map<String, Object> o)
				throws IOException;

		/**
		 * Write an entry to an archive.
		 *
		 * @param out
		 *            archive object from createArchiveOutputStream
		 * @param tree
		 *            the tag, commit, or tree object to produce an archive for
		 * @param path
		 *            full filename relative to the root of the archive (with
		 *            trailing '/' for directories)
		 * @param mode
		 *            mode (for example FileMode.REGULAR_FILE or
		 *            FileMode.SYMLINK)
		 * @param loader
		 *            blob object with data for this entry (null for
		 *            directories)
		 * @throws IOException
		 *             thrown by the underlying output stream for I/O errors
		 * @since 4.7
		 */
		void putEntry(T out, ObjectId tree, String path, FileMode mode,
				ObjectLoader loader) throws IOException;

		/**
		 * Filename suffixes representing this format (e.g.,
		 * { ".tar.gz", ".tgz" }).
		 *
		 * The behavior is undefined when suffixes overlap (if
		 * one format claims suffix ".7z", no other format should
		 * take ".tar.7z").
		 *
		 * @return this format's suffixes
		 */
		Iterable<String> suffixes();
	}

	/**
	 * Signals an attempt to use an archival format that ArchiveCommand
	 * doesn't know about (for example due to a typo).
	 */
	public static class UnsupportedFormatException extends GitAPIException {
		private static final long serialVersionUID = 1L;

		private final String format;

		/**
		 * @param format the problematic format name
		 */
		public UnsupportedFormatException(String format) {
			super(MessageFormat.format(JGitText.get().unsupportedArchiveFormat, format));
			this.format = format;
		}

		/**
		 * @return the problematic format name
		 */
		public String getFormat() {
			return format;
		}
	}

	private static class FormatEntry {
		final Format<?> format;
		/** Number of times this format has been registered. */
		final int refcnt;

		public FormatEntry(Format<?> format, int refcnt) {
			if (format == null)
				throw new NullPointerException();
			this.format = format;
			this.refcnt = refcnt;
		}
	}

	/**
	 * Available archival formats (corresponding to values for
	 * the --format= option)
	 */
	private static final Map<String, FormatEntry> formats =
			new ConcurrentHashMap<>();

	/**
	 * Replaces the entry for a key only if currently mapped to a given
	 * value.
	 *
	 * @param map a map
	 * @param key key with which the specified value is associated
	 * @param oldValue expected value for the key (null if should be absent).
	 * @param newValue value to be associated with the key (null to remove).
	 * @return true if the value was replaced
	 */
	private static <K, V> boolean replace(Map<K, V> map,
			K key, V oldValue, V newValue) {
		if (oldValue == null && newValue == null) // Nothing to do.
			return true;

		if (oldValue == null)
			return map.putIfAbsent(key, newValue) == null;
		else if (newValue == null)
			return map.remove(key, oldValue);
		else
			return map.replace(key, oldValue, newValue);
	}

	/**
	 * Adds support for an additional archival format.  To avoid
	 * unnecessary dependencies, ArchiveCommand does not have support
	 * for any formats built in; use this function to add them.
	 * <p>
	 * OSGi plugins providing formats should call this function at
	 * bundle activation time.
	 * <p>
	 * It is okay to register the same archive format with the same
	 * name multiple times, but don't forget to unregister it that
	 * same number of times, too.
	 * <p>
	 * Registering multiple formats with different names and the
	 * same or overlapping suffixes results in undefined behavior.
	 * TODO: check that suffixes don't overlap.
	 *
	 * @param name name of a format (e.g., "tar" or "zip").
	 * @param fmt archiver for that format
	 * @throws JGitInternalException
	 *              A different archival format with that name was
	 *              already registered.
	 */
	public static void registerFormat(String name, Format<?> fmt) {
		if (fmt == null)
			throw new NullPointerException();

		FormatEntry old, entry;
		do {
			old = formats.get(name);
			if (old == null) {
				entry = new FormatEntry(fmt, 1);
				continue;
			}
			if (!old.format.equals(fmt))
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().archiveFormatAlreadyRegistered,
						name));
			entry = new FormatEntry(old.format, old.refcnt + 1);
		} while (!replace(formats, name, old, entry));
	}

	/**
	 * Marks support for an archival format as no longer needed so its
	 * Format can be garbage collected if no one else is using it either.
	 * <p>
	 * In other words, this decrements the reference count for an
	 * archival format.  If the reference count becomes zero, removes
	 * support for that format.
	 *
	 * @param name name of format (e.g., "tar" or "zip").
	 * @throws JGitInternalException
	 *              No such archival format was registered.
	 */
	public static void unregisterFormat(String name) {
		FormatEntry old, entry;
		do {
			old = formats.get(name);
			if (old == null)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().archiveFormatAlreadyAbsent,
						name));
			if (old.refcnt == 1) {
				entry = null;
				continue;
			}
			entry = new FormatEntry(old.format, old.refcnt - 1);
		} while (!replace(formats, name, old, entry));
	}

	private static Format<?> formatBySuffix(String filenameSuffix)
			throws UnsupportedFormatException {
		if (filenameSuffix != null)
			for (FormatEntry entry : formats.values()) {
				Format<?> fmt = entry.format;
				for (String sfx : fmt.suffixes())
					if (filenameSuffix.endsWith(sfx))
						return fmt;
			}
		return lookupFormat("tar"); //$NON-NLS-1$
	}

	private static Format<?> lookupFormat(String formatName) throws UnsupportedFormatException {
		FormatEntry entry = formats.get(formatName);
		if (entry == null)
			throw new UnsupportedFormatException(formatName);
		return entry.format;
	}

	private OutputStream out;
	private ObjectId tree;
	private String prefix;
	private String format;
	private Map<String, Object> formatOptions = new HashMap<>();
	private List<String> paths = new ArrayList<>();

	/** Filename suffix, for automatically choosing a format. */
	private String suffix;

	/**
	 * Constructor for ArchiveCommand
	 *
	 * @param repo
	 *            the {@link org.eclipse.jgit.lib.Repository}
	 */
	public ArchiveCommand(Repository repo) {
		super(repo);
		setCallable(false);
	}

	private <T extends Closeable> OutputStream writeArchive(Format<T> fmt) {
		try {
			try (TreeWalk walk = new TreeWalk(repo);
					RevWalk rw = new RevWalk(walk.getObjectReader());
					T outa = fmt.createArchiveOutputStream(out,
							formatOptions)) {
				String pfx = prefix == null ? "" : prefix; //$NON-NLS-1$
				MutableObjectId idBuf = new MutableObjectId();
				ObjectReader reader = walk.getObjectReader();

				RevObject o = rw.peel(rw.parseAny(tree));
				walk.reset(getTree(o));
				if (!paths.isEmpty()) {
					walk.setFilter(PathFilterGroup.createFromStrings(paths));
				}

				// Put base directory into archive
				if (pfx.endsWith("/")) { //$NON-NLS-1$
					fmt.putEntry(outa, o, pfx.replaceAll("[/]+$", "/"), //$NON-NLS-1$ //$NON-NLS-2$
							FileMode.TREE, null);
				}

				while (walk.next()) {
					String name = pfx + walk.getPathString();
					FileMode mode = walk.getFileMode(0);

					if (walk.isSubtree())
						walk.enterSubtree();

					if (mode == FileMode.GITLINK) {
						// TODO(jrn): Take a callback to recurse
						// into submodules.
						mode = FileMode.TREE;
					}

					if (mode == FileMode.TREE) {
						fmt.putEntry(outa, o, name + "/", mode, null); //$NON-NLS-1$
						continue;
					}
					walk.getObjectId(idBuf, 0);
					fmt.putEntry(outa, o, name, mode, reader.open(idBuf));
				}
				return out;
			} finally {
				out.close();
			}
		} catch (IOException e) {
			// TODO(jrn): Throw finer-grained errors.
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfArchiveCommand, e);
		}
	}

	@Override
	public OutputStream call() throws GitAPIException {
		checkCallable();

		Format<?> fmt;
		if (format == null)
			fmt = formatBySuffix(suffix);
		else
			fmt = lookupFormat(format);
		return writeArchive(fmt);
	}

	/**
	 * Set the tag, commit, or tree object to produce an archive for
	 *
	 * @param tree
	 *            the tag, commit, or tree object to produce an archive for
	 * @return this
	 */
	public ArchiveCommand setTree(ObjectId tree) {
		if (tree == null)
			throw new IllegalArgumentException();

		this.tree = tree;
		setCallable(true);
		return this;
	}

	/**
	 * Set string prefixed to filenames in archive
	 *
	 * @param prefix
	 *            string prefixed to filenames in archive (e.g., "master/").
	 *            null means to not use any leading prefix.
	 * @return this
	 * @since 3.3
	 */
	public ArchiveCommand setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	/**
	 * Set the intended filename for the produced archive. Currently the only
	 * effect is to determine the default archive format when none is specified
	 * with {@link #setFormat(String)}.
	 *
	 * @param filename
	 *            intended filename for the archive
	 * @return this
	 */
	public ArchiveCommand setFilename(String filename) {
		int slash = filename.lastIndexOf('/');
		int dot = filename.indexOf('.', slash + 1);

		if (dot == -1)
			this.suffix = ""; //$NON-NLS-1$
		else
			this.suffix = filename.substring(dot);
		return this;
	}

	/**
	 * Set output stream
	 *
	 * @param out
	 *            the stream to which to write the archive
	 * @return this
	 */
	public ArchiveCommand setOutputStream(OutputStream out) {
		this.out = out;
		return this;
	}

	/**
	 * Set archive format
	 *
	 * @param fmt
	 *            archive format (e.g., "tar" or "zip"). null means to choose
	 *            automatically based on the archive filename.
	 * @return this
	 */
	public ArchiveCommand setFormat(String fmt) {
		this.format = fmt;
		return this;
	}

	/**
	 * Set archive format options
	 *
	 * @param options
	 *            archive format options (e.g., level=9 for zip compression).
	 * @return this
	 * @since 4.0
	 */
	public ArchiveCommand setFormatOptions(Map<String, Object> options) {
		this.formatOptions = options;
		return this;
	}

	/**
	 * Set an optional parameter path. without an optional path parameter, all
	 * files and subdirectories of the current working directory are included in
	 * the archive. If one or more paths are specified, only these are included.
	 *
	 * @param paths
	 *            file names (e.g <code>file1.c</code>) or directory names (e.g.
	 *            <code>dir</code> to add <code>dir/file1</code> and
	 *            <code>dir/file2</code>) can also be given to add all files in
	 *            the directory, recursively. Fileglobs (e.g. *.c) are not yet
	 *            supported.
	 * @return this
	 * @since 3.4
	 */
	public ArchiveCommand setPaths(String... paths) {
		this.paths = Arrays.asList(paths);
		return this;
	}

	private RevTree getTree(RevObject o)
			throws IncorrectObjectTypeException {
		final RevTree t;
		if (o instanceof RevCommit) {
			t = ((RevCommit) o).getTree();
		} else if (!(o instanceof RevTree)) {
			throw new IncorrectObjectTypeException(tree.toObjectId(),
					Constants.TYPE_TREE);
		} else {
			t = (RevTree) o;
		}
		return t;
	}

}
