@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.RemoteRemoveCommand
import org.eclipse.jgit.transport.RemoteConfig

/**
 * @see RemoteRemoveCommand
 */
class KRemoteRemoveCommand(asJ: RemoteRemoveCommand): KGitCommand<RemoteRemoveCommand, RemoteConfig>(asJ) {
    /**
     * @see RemoteRemoveCommand.setRemoteName
     */
    fun setRemoteName(remoteName: String) {
        asJ.setRemoteName(remoteName)
    }
}