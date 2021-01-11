@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.DiffCommand
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.treewalk.AbstractTreeIterator
import org.eclipse.jgit.treewalk.filter.TreeFilter
import java.io.OutputStream

/**
 * @see DiffCommand
 */
class KDiffCommand(asJ: DiffCommand) : KGitCommand<DiffCommand, List<DiffEntry>>(asJ) {
    /**
     * @see DiffCommand.setCached
     */
    fun setCached(cached: Boolean) {
        asJ.setCached(cached)
    }

    /**
     * @see DiffCommand.setPathFilter
     */
    fun setPathFilter(pathFilter: TreeFilter?) {
        asJ.setPathFilter(pathFilter)
    }

    /**
     * @see DiffCommand.setOldTree
     */
    fun setOldTree(oldTree: AbstractTreeIterator?) {
        asJ.setOldTree(oldTree)
    }

    /**
     * @see DiffCommand.setNewTree
     */
    fun setNewTree(newTree: AbstractTreeIterator?) {
        asJ.setNewTree(newTree)
    }

    /**
     * @see DiffCommand.setShowNameAndStatusOnly
     */
    fun setShowNameAndStatusOnly(showNameAndStatusOnly: Boolean) {
        asJ.setShowNameAndStatusOnly(showNameAndStatusOnly)
    }

    /**
     * @see DiffCommand.setOutputStream
     */
    fun setOutputStream(out: OutputStream) {
        asJ.setOutputStream(out)
    }

    /**
     * @see DiffCommand.setContextLines
     */
    fun setContextLines(contextLines: Int) {
        asJ.setContextLines(contextLines)
    }

    /**
     * @see DiffCommand.setSourcePrefix
     */
    fun setSourcePrefix(sourcePrefix: String?) {
        asJ.setSourcePrefix(sourcePrefix)
    }

    /**
     * @see DiffCommand.setDestinationPrefix
     */
    fun setDestinationPrefix(destinationPrefix: String?) {
        asJ.setDestinationPrefix(destinationPrefix)
    }

    /**
     * @see DiffCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }
}
