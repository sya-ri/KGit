@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.transport.TagOpt
import org.eclipse.jgit.util.FS
import java.io.File
import java.time.Instant
import java.time.OffsetDateTime

/**
 * @see CloneCommand
 */
class KCloneCommand(asJ: CloneCommand = CloneCommand()) :
    KTransportCommand<CloneCommand, Git>(asJ),
    KWrapperCallable<KGit, Git> {
    /**
     * @see CloneCommand.call
     */
    override fun callAsK() = KGit(call())

    /**
     * @see CloneCommand.setURI
     */
    fun setURI(uri: String?) {
        asJ.setURI(uri)
    }

    /**
     * @see CloneCommand.setDirectory
     */
    fun setDirectory(directory: File?) {
        asJ.setDirectory(directory)
    }

    /**
     * @see CloneCommand.setGitDir
     */
    fun setGitDir(gitDir: File?) {
        asJ.setGitDir(gitDir)
    }

    /**
     * @see CloneCommand.setBare
     */
    fun setBare(bare: Boolean) {
        asJ.setBare(bare)
    }

    /**
     * @see CloneCommand.setFs
     */
    fun setFs(fs: FS?) {
        asJ.setFs(fs)
    }

    /**
     * @see CloneCommand.setRemote
     */
    fun setRemote(remote: String?) {
        asJ.setRemote(remote)
    }

    /**
     * @see CloneCommand.setBranch
     */
    fun setBranch(branch: String?) {
        asJ.setBranch(branch)
    }

    /**
     * @see CloneCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }

    /**
     * @see CloneCommand.setCloneAllBranches
     */
    fun setCloneAllBranches(cloneAllBranches: Boolean) {
        asJ.setCloneAllBranches(cloneAllBranches)
    }

    /**
     * @see CloneCommand.setMirror
     */
    fun setMirror(mirror: Boolean) {
        asJ.setMirror(mirror)
    }

    /**
     * @see CloneCommand.setCloneSubmodules
     */
    fun setCloneSubmodules(cloneSubmodules: Boolean) {
        asJ.setCloneSubmodules(cloneSubmodules)
    }

    /**
     * @see CloneCommand.setBranchesToClone
     */
    fun setBranchesToClone(branchesToClone: Collection<String>?) {
        asJ.setBranchesToClone(branchesToClone)
    }

    /**
     * @see CloneCommand.setTagOption
     */
    fun setTagOption(tagOption: TagOpt?) {
        asJ.setTagOption(tagOption)
    }

    /**
     * @see CloneCommand.setNoCheckout
     */
    fun setNoCheckout(noCheckout: Boolean) {
        asJ.setNoCheckout(noCheckout)
    }

    /**
     * @see CloneCommand.setCallback
     */
    fun setCallback(callback: CloneCommand.Callback?) {
        asJ.setCallback(callback)
    }

    /**
     * @see CloneCommand.setDepth
     */
    fun setDepth(depth: Int) {
        asJ.setDepth(depth)
    }

    /**
     * @see CloneCommand.setShallowSince
     */
    fun setShallowSince(shallowSince: OffsetDateTime) {
        asJ.setShallowSince(shallowSince)
    }

    /**
     * @see CloneCommand.setShallowSince
     */
    fun setShallowSince(shallowSince: Instant) {
        asJ.setShallowSince(shallowSince)
    }

    /**
     * @see CloneCommand.addShallowExclude
     */
    fun addShallowExclude(shallowExclude: String) {
        asJ.addShallowExclude(shallowExclude)
    }

    /**
     * @see CloneCommand.addShallowExclude
     */
    fun addShallowExclude(shallowExclude: ObjectId) {
        asJ.addShallowExclude(shallowExclude)
    }
}
