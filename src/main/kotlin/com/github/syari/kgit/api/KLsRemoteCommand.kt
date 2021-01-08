package com.github.syari.kgit.api

import org.eclipse.jgit.api.LsRemoteCommand
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository

class KLsRemoteCommand(private val asJ: LsRemoteCommand) {
    constructor(repo: Repository? = null): this(LsRemoteCommand(repo))

    /**
     * @see LsRemoteCommand.call
     */
    fun call(): Collection<Ref> = asJ.call()

    /**
     * @see LsRemoteCommand.setRemote
     */
    var remote: String = Constants.DEFAULT_REMOTE_NAME
        set(value) {
            field = value.apply(asJ::setRemote)
        }

    /**
     * @see LsRemoteCommand.setHeads
     */
    var heads: Boolean = false
        set(value) {
            field = value.apply(asJ::setHeads)
        }

    /**
     * @see LsRemoteCommand.setTags
     */
    var tags: Boolean = false
        set(value) {
            field = value.apply(asJ::setTags)
        }

    /**
     * @see LsRemoteCommand.setUploadPack
     */
    var uploadPack: String? = null
        set(value) {
            field = value.apply(asJ::setUploadPack)
        }

    /**
     * @see LsRemoteCommand.callAsMap
     */
    fun callAsMap(): Map<String, Ref> = asJ.callAsMap()
}