package com.github.syari.kgit

import org.eclipse.jgit.api.ListTagCommand
import org.eclipse.jgit.lib.Ref

/**
 * @see ListTagCommand
 */
class KListTagCommand(private val asJ: ListTagCommand) {
    /**
     * @see ListTagCommand.call
     */
    fun call(): List<Ref> = asJ.call()
}