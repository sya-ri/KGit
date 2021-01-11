@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.StashDropCommand
import org.eclipse.jgit.lib.ObjectId

/**
 * @see StashDropCommand
 */
class KStashDropCommand(asJ: StashDropCommand) : KGitCommand<StashDropCommand, ObjectId>(asJ) {
    /**
     * @see StashDropCommand.setStashRef
     */
    fun setStashRef(stashRef: Int) {
        asJ.setStashRef(stashRef)
    }

    /**
     * @see StashDropCommand.setAll
     */
    fun setAll(all: Boolean) {
        asJ.setAll(all)
    }
}
