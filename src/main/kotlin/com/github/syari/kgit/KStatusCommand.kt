@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.Status
import org.eclipse.jgit.api.StatusCommand
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.submodule.SubmoduleWalk
import org.eclipse.jgit.treewalk.WorkingTreeIterator

/**
 * @see StatusCommand
 */
class KStatusCommand(asJ: StatusCommand) : KGitCommand<StatusCommand, Status>(asJ) {
    /**
     * @see StatusCommand.setIgnoreSubmodules
     */
    fun setIgnoreSubmodules(ignoreSubmoduleMode: SubmoduleWalk.IgnoreSubmoduleMode?) {
        asJ.setIgnoreSubmodules(ignoreSubmoduleMode)
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
    fun setWorkingTreeIt(workingTreeIt: WorkingTreeIterator?) {
        asJ.setWorkingTreeIt(workingTreeIt)
    }

    /**
     * @see StatusCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }
}
