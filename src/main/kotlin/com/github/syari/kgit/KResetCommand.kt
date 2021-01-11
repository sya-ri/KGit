@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.ResetCommand
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref

/**
 * @see ResetCommand
 */
class KResetCommand(asJ: ResetCommand) : KGitCommand<ResetCommand, Ref?>(asJ) {
    /**
     * @see ResetCommand.setRef
     */
    fun setRef(ref: String?) {
        asJ.setRef(ref)
    }

    /**
     * @see ResetCommand.setMode
     */
    fun setMode(mode: ResetCommand.ResetType?) {
        asJ.setMode(mode)
    }

    /**
     * @see ResetCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }

    /**
     * @see ResetCommand.disableRefLog
     * @see ResetCommand.isReflogDisabled
     */
    var isReflogDisabled: Boolean
        set(value) {
            asJ.disableRefLog(value)
        }
        get() = asJ.isReflogDisabled

    /**
     * @see ResetCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }
}
