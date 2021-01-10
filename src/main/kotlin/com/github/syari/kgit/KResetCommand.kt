package com.github.syari.kgit

import org.eclipse.jgit.api.ResetCommand
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref

/**
 * @see ResetCommand
 */
class KResetCommand(private val asJ: ResetCommand) {
    /**
     * @see ResetCommand.call
     */
    fun call(): Ref? = asJ.call()

    /**
     * @see ResetCommand.setRef
     */
    var ref: String? = null
        set(value) {
            field = value.apply(asJ::setRef)
        }

    /**
     * @see ResetCommand.setMode
     */
    var mode: ResetCommand.ResetType? = null
        set(value) {
            field = value.apply(asJ::setMode)
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
    var progressMonitor: ProgressMonitor = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
        }
}