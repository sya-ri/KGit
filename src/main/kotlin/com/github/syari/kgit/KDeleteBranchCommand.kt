@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.DeleteBranchCommand
import org.eclipse.jgit.lib.ProgressMonitor

/**
 * @see DeleteBranchCommand
 */
class KDeleteBranchCommand(asJ: DeleteBranchCommand) : KGitCommand<DeleteBranchCommand, List<String>>(asJ) {
    /**
     * @see DeleteBranchCommand.setBranchNames
     */
    fun setBranchNames(vararg branchnames: String) {
        asJ.setBranchNames(*branchnames)
    }

    /**
     * @see DeleteBranchCommand.setBranchNames
     */
    fun setBranchNames(branchNames: Collection<String>) {
        asJ.setBranchNames(branchNames)
    }

    /**
     * @see DeleteBranchCommand.setForce
     */
    fun setForce(force: Boolean) {
        asJ.setForce(force)
    }

    /**
     * @see DeleteBranchCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.progressMonitor = monitor
    }

    /**
     * @see DeleteBranchCommand.getProgressMonitor
     */
    val progressMonitor: ProgressMonitor by asJ::progressMonitor
}
