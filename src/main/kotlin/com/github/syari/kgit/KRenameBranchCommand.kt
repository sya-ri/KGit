package com.github.syari.kgit

import org.eclipse.jgit.api.RenameBranchCommand
import org.eclipse.jgit.lib.Ref

/**
 * @see RenameBranchCommand
 */
class KRenameBranchCommand(private val asJ: RenameBranchCommand) {
    /**
     * @see RenameBranchCommand.call
     */
    fun call(): Ref = asJ.call()

    /**
     * @see RenameBranchCommand.setNewName
     */
    fun setNewName(newName: String?) {
        asJ.setNewName(newName)
    }

    /**
     * @see RenameBranchCommand.setOldName
     */
    fun setOldName(oldName: String?) {
        asJ.setOldName(oldName)
    }
}