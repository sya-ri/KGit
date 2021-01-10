package com.github.syari.kgit

import org.eclipse.jgit.api.RmCommand
import org.eclipse.jgit.dircache.DirCache

/**
 * @see RmCommand
 */
class KRmCommand(private val asJ: RmCommand) {
    /**
     * @see RmCommand.call
     */
    fun call(): DirCache? = asJ.call()

    /**
     * @see RmCommand.addFilepattern
     */
    fun addFilepattern(filepattern: String) {
        asJ.addFilepattern(filepattern)
    }

    /**
     * @see RmCommand.setCached
     */
    fun setCached(cached: Boolean) {
        asJ.setCached(cached)
    }
}