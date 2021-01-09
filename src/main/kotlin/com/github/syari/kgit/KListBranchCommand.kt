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
    var listMode: ListBranchCommand.ListMode? = null
        set(value) {
            field = value.apply(asJ::setListMode)
        }

    /**
     * @see ListBranchCommand.setContains
     */
    var contains: String? = null
        set(value) {
            field = value.apply(asJ::setContains)
        }
}