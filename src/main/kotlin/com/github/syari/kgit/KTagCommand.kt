package com.github.syari.kgit

import org.eclipse.jgit.api.TagCommand
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.revwalk.RevObject

/**
 * @see TagCommand
 */
class KTagCommand(private val asJ: TagCommand) {
    /**
     * @see TagCommand.call
     */
    fun call(): Ref = asJ.call()

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
    var signed: Boolean
        set(value) {
            asJ.isSigned = value
        }
        get() = asJ.isSigned

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
    var forceUpdate: Boolean
        set(value) {
            asJ.isForceUpdate = value
        }
        get() = asJ.isForceUpdate

    /**
     * @see TagCommand.setAnnotated
     * @see TagCommand.isAnnotated
     */
    var annotated: Boolean
        set(value) {
            asJ.isAnnotated = value
        }
        get() = asJ.isAnnotated
}