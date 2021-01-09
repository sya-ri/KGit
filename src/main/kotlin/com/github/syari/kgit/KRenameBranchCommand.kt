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
    var newName: String? = null
        set(value) {
            field = value.apply(asJ::setNewName)
        }

    /**
     * @see RenameBranchCommand.setOldName
     */
    var oldName: String? = null
        set(value) {
            field = value.apply(asJ::setOldName)
        }
}