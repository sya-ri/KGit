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
    var branchNames: Array<String> = arrayOf()
        set(value) {
            field = value.apply(asJ::setBranchNames)
        }

    /**
     * @see DeleteBranchCommand.setForce
     */
    var force: Boolean = false
        set(value) {
            field = value.apply(asJ::setForce)
        }
}