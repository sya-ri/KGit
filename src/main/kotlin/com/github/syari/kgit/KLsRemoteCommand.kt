package com.github.syari.kgit

import org.eclipse.jgit.api.LsRemoteCommand
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository

/**
 * @see LsRemoteCommand
 */
class KLsRemoteCommand(private val asJ: LsRemoteCommand) {
    constructor(repo: Repository? = null): this(LsRemoteCommand(repo))

    /**
     * @see LsRemoteCommand.call
     */
    fun call(): Collection<Ref> = asJ.call()

    /**
     * @see LsRemoteCommand.setRemote
     */
    fun setRemote(remote: String) {
        asJ.setRemote(remote)
    }

    /**
     * @see LsRemoteCommand.setHeads
     */
    fun setHeads(heads: Boolean) {
        asJ.setHeads(heads)
    }

    /**
     * @see LsRemoteCommand.setTags
     */
    fun setTags(tags: Boolean) {
        asJ.setTags(tags)
    }

    /**
     * @see LsRemoteCommand.setUploadPack
     */
    fun setUploadPack(uploadPack: String?) {
        asJ.setUploadPack(uploadPack)
    }

    /**
     * @see LsRemoteCommand.callAsMap
     */
    fun callAsMap(): Map<String, Ref> = asJ.callAsMap()
}