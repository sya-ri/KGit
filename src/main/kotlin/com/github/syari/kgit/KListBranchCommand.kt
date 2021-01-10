package com.github.syari.kgit

import org.eclipse.jgit.api.ListBranchCommand
import org.eclipse.jgit.lib.Ref

/**
 * @see ListBranchCommand
 */
class KListBranchCommand(private val asJ: ListBranchCommand) {
    /**
     * @see ListBranchCommand.call
     */
    fun call(): List<Ref> = asJ.call()

    /**
     * @see ListBranchCommand.setListMode
     */
    fun setListMode(listMode:ListBranchCommand.ListMode?) {
        asJ.setListMode(listMode)
    }

    /**
     * @see ListBranchCommand.setContains
     */
    fun setContains(containsCommitish: String?) {
        asJ.setContains(containsCommitish)
    }
}