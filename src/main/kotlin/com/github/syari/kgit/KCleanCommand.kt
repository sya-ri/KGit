@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.CleanCommand

/**
 * @see CleanCommand
 */
class KCleanCommand(asJ: CleanCommand): KGitCommand<CleanCommand, Set<String>>(asJ) {
    /**
     * @see CleanCommand.setPaths
     */
    fun setPaths(paths: Set<String>) {
        asJ.setPaths(paths)
    }

    /**
     * @see CleanCommand.setDryRun
     */
    fun setDryRun(dryRun: Boolean) {
        asJ.setDryRun(dryRun)
    }

    /**
     * @see CleanCommand.setForce
     */
    fun setForce(force: Boolean) {
        asJ.setForce(force)
    }

    /**
     * @see CleanCommand.setCleanDirectories
     */
    fun setCleanDirectories(dirs: Boolean) {
        asJ.setCleanDirectories(dirs)
    }

    /**
     * @see CleanCommand.setIgnore
     */
    fun setIgnore(ignore: Boolean) {
        asJ.setIgnore(ignore)
    }
}