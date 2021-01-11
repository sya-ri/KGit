@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.SubmoduleSyncCommand

/**
 * @see SubmoduleSyncCommand
 */
class KSubmoduleSyncCommand(asJ: SubmoduleSyncCommand): KGitCommand<SubmoduleSyncCommand, Map<String, String>>(asJ) {
    /**
     * @see SubmoduleSyncCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }
}