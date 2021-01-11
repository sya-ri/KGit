@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.RemoteAddCommand
import org.eclipse.jgit.transport.RemoteConfig
import org.eclipse.jgit.transport.URIish

/**
 * @see RemoteAddCommand
 */
class KRemoteAddCommand(asJ: RemoteAddCommand): KGitCommand<RemoteAddCommand, RemoteConfig>(asJ) {
    /**
     * @see RemoteAddCommand
     */
    fun setName(name: String?) {
        asJ.setName(name)
    }

    /**
     * @see RemoteAddCommand.setUri
     */
    fun setUri(uri: URIish?) {
        asJ.setUri(uri)
    }
}