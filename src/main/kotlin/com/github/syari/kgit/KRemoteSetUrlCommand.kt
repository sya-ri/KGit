@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.RemoteSetUrlCommand
import org.eclipse.jgit.api.RemoteSetUrlCommand.UriType
import org.eclipse.jgit.transport.RemoteConfig
import org.eclipse.jgit.transport.URIish

/**
 * @see RemoteSetUrlCommand
 */
class KRemoteSetUrlCommand(asJ: RemoteSetUrlCommand) : KGitCommand<RemoteSetUrlCommand, RemoteConfig>(asJ) {
    /**
     * @see RemoteSetUrlCommand
     */
    fun setRemoteName(remoteName: String) {
        asJ.setRemoteName(remoteName)
    }

    /**
     * @see RemoteSetUrlCommand
     */
    fun setRemoteUri(remoteUri: URIish) {
        asJ.setRemoteUri(remoteUri)
    }

    /**
     * @see RemoteSetUrlCommand
     */
    fun setUriType(type: UriType) {
        asJ.setUriType(type)
    }
}
