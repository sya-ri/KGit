package com.github.syari.kgit

import org.eclipse.jgit.api.Status
import org.eclipse.jgit.api.StatusCommand
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.submodule.SubmoduleWalk
import org.eclipse.jgit.treewalk.WorkingTreeIterator

/**
 * @see StatusCommand
 */
class KStatusCommand(private val asJ: StatusCommand) {
    /**
     * @see StatusCommand.call
     */
    fun call(): Status = asJ.call()

    /**
     * @see StatusCommand.setIgnoreSubmodules
     */
    var ignoreSubmoduleMode: SubmoduleWalk.IgnoreSubmoduleMode? = null
        set(value) {
            field = value.apply(asJ::setIgnoreSubmodules)
        }

    /**
     * @see StatusCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }

    /**
     * @see StatusCommand.getPaths
     */
    val paths: List<String>? by asJ::paths

    /**
     * @see StatusCommand.setWorkingTreeIt
     */
    var workingTreeIt: WorkingTreeIterator? = null
        set(value) {
            field = value.apply(asJ::setWorkingTreeIt)
        }

    /**
     * @see StatusCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
        }
}