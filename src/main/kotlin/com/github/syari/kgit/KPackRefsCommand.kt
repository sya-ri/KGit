@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.PackRefsCommand
import org.eclipse.jgit.lib.ProgressMonitor

/**
 * @see PackRefsCommand
 */
class KPackRefsCommand(asJ: PackRefsCommand) : KGitCommand<PackRefsCommand, String>(asJ) {
    /**
     * @see PackRefsCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor) {
        asJ.setProgressMonitor(monitor)
    }

    /**
     * @see PackRefsCommand.setAll
     * @see PackRefsCommand.isAll
     */
    var isAll: Boolean
        set(value) {
            asJ.isAll = value
        }
        get() = asJ.isAll
}
