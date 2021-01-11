@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.DeleteBranchCommand

/**
 * @see DeleteBranchCommand
 */
class KDeleteBranchCommand(asJ: DeleteBranchCommand): KGitCommand<DeleteBranchCommand, List<String>>(asJ) {
    /**
     * @see DeleteBranchCommand.setBranchNames
     */
    fun setBranchNames(vararg branchnames: String) {
        asJ.setBranchNames(*branchnames)
    }

    /**
     * @see DeleteBranchCommand.setForce
     */
    fun setForce(force: Boolean) {
        asJ.setForce(force)
    }
}