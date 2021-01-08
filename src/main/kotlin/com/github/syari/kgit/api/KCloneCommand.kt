package com.github.syari.kgit.api

import com.github.syari.kgit.KGit
import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.transport.TagOpt
import org.eclipse.jgit.util.FS
import java.io.File

/**
 * @see CloneCommand
 */
class KCloneCommand(val asJ: CloneCommand = CloneCommand()) {
    /**
     * @see CloneCommand.call
     */
    fun call() = KGit(asJ.call())

    /**
     * @see CloneCommand.setURI
     */
    var uri: String? = null
        set(value) {
            field = value.apply(asJ::setURI)
        }

    /**
     * @see CloneCommand.setDirectory
     */
    var directory: File? = null
        set(value) {
            field = value.apply(asJ::setDirectory)
        }

    /**
     * @see CloneCommand.setGitDir
     */
    var gitDir: File? = null
        set(value) {
            field = value.apply(asJ::setGitDir)
        }

    /**
     * @see CloneCommand.setBare
     */
    var bare: Boolean = false
        set(value) {
            field = value.apply(asJ::setBare)
        }

    /**
     * @see CloneCommand.setFs
     */
    var fs: FS? = null
        set(value) {
            field = value.apply(asJ::setFs)
        }

    /**
     * @see CloneCommand.setRemote
     */
    var remote: String = Constants.DEFAULT_REMOTE_NAME
        set(value) {
            field = value.apply(asJ::setRemote)
        }

    /**
     * @see CloneCommand.setBranch
     */
    var branch: String = Constants.HEAD
    set(value) {
            field = value.apply(asJ::setBranch)
        }

    /**
     * @see CloneCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
        }

    /**
     * @see CloneCommand.setCloneAllBranches
     */
    var cloneAllBranches: Boolean = false
        set(value) {
            field = value.apply(asJ::setCloneAllBranches)
        }

    /**
     * @see CloneCommand.setMirror
     */
    var mirror: Boolean = false
        set(value) {
            field = value.apply(asJ::setMirror)
        }

    /**
     * @see CloneCommand.setCloneSubmodules
     */
    var cloneSubmodules: Boolean = false
        set(value) {
            field = value.apply(asJ::setCloneSubmodules)
        }

    /**
     * @see CloneCommand.setBranchesToClone
     */
    var branchesToClone: Collection<String>? = null
        set(value) {
            field = value.apply(asJ::setBranchesToClone)
        }

    /**
     * @see CloneCommand.setTagOption
     */
    var tagOption: TagOpt? = null
        set(value) {
            field = value.apply(asJ::setTagOption)
        }

    /**
     * @see CloneCommand.setNoCheckout
     */
    var noCheckout: Boolean = false
        set(value) {
            field = value.apply(asJ::setNoCheckout)
        }

    /**
     * @see CloneCommand.Callback
     */
    var callback: CloneCommand.Callback? = null
        set(value) {
            field = value.apply(asJ::setCallback)
        }
}