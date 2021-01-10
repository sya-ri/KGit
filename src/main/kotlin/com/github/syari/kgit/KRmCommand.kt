package com.github.syari.kgit

import org.eclipse.jgit.api.RmCommand
import org.eclipse.jgit.dircache.DirCache

/**
 * @see RmCommand
 */
class KRmCommand(asJ: RmCommand): KGitCommand<RmCommand, DirCache?>(asJ) {
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