@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.TagCommand
import org.eclipse.jgit.lib.GpgConfig
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Signer
import org.eclipse.jgit.revwalk.RevObject
import org.eclipse.jgit.transport.CredentialsProvider

/**
 * @see TagCommand
 */
class KTagCommand(asJ: TagCommand) : KGitCommand<TagCommand, Ref>(asJ) {
    /**
     * @see TagCommand.setName
     * @see TagCommand.getName
     */
    var name: String by asJ::name

    /**
     * @see TagCommand.setMessage
     * @see TagCommand.getMessage
     */
    var message: String by asJ::message

    /**
     * @see TagCommand.setSigned
     * @see TagCommand.isSigned
     */
    var isSigned: Boolean
        set(value) {
            asJ.isSigned = value
        }
        get() = asJ.isSigned

    /**
     * @see TagCommand.setSigner
     */
    fun setSigner(signer: Signer?) {
        asJ.setSigner(signer)
    }

    /**
     * @see TagCommand.setGpgConfig
     */
    fun setGpgConfig(config: GpgConfig?) {
        asJ.setGpgConfig(config)
    }

    /**
     * @see TagCommand.setTagger
     * @see TagCommand.getTagger
     */
    var tagger: PersonIdent by asJ::tagger

    /**
     * @see TagCommand.setObjectId
     * @see TagCommand.getObjectId
     */
    var objectId: RevObject by asJ::objectId

    /**
     * @see TagCommand.setForceUpdate
     * @see TagCommand.isForceUpdate
     */
    var isForceUpdate: Boolean
        set(value) {
            asJ.isForceUpdate = value
        }
        get() = asJ.isForceUpdate

    /**
     * @see TagCommand.setAnnotated
     * @see TagCommand.isAnnotated
     */
    var isAnnotated: Boolean
        set(value) {
            asJ.isAnnotated = value
        }
        get() = asJ.isAnnotated

    /**
     * @see TagCommand.setSigningKey
     * @see TagCommand.getSigningKey
     */
    var signingKey: String? by asJ::signingKey

    /**
     * @see TagCommand.setCredentialsProvider
     */
    fun setCredentialsProvider(credentialsProvider: CredentialsProvider?) {
        asJ.setCredentialsProvider(credentialsProvider)
    }
}
