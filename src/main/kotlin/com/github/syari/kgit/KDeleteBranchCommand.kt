package com.github.syari.kgit

import org.eclipse.jgit.api.DeleteBranchCommand

/**
 * @see DeleteBranchCommand
 */
class KDeleteBranchCommand(private val asJ: DeleteBranchCommand) {
    /**
     * @see DeleteBranchCommand.call
     */
    fun call(): List<String> = asJ.call()

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