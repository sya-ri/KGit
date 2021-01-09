package com.github.syari.kgit

import org.eclipse.jgit.api.FetchCommand
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.SubmoduleConfig
import org.eclipse.jgit.transport.FetchResult
import org.eclipse.jgit.transport.RefSpec
import org.eclipse.jgit.transport.TagOpt

/**
 * @see FetchCommand
 */
class KFetchCommand(private val asJ: FetchCommand) {
    /**
     * @see FetchCommand.call
     */
    fun call(): FetchResult = asJ.call()

    /**
     * @see FetchCommand.setRecurseSubmodules
     */
    var submoduleRecurseMode: SubmoduleConfig.FetchRecurseSubmodulesMode? = null
        set(value) {
            field = value.apply(asJ::setRecurseSubmodules)
        }

    /**
     * @see FetchCommand.setRemote
     * @see FetchCommand.getRemote
     */
    var remote: String by asJ::remote

    /**
     * @see FetchCommand.getTimeout
     * @see FetchCommand.setTimeout
     */
    var timeout: Int by asJ::timeout

    /**
     * @see FetchCommand.setCheckFetchedObjects
     * @see FetchCommand.isCheckFetchedObjects
     */
    var checkFetchedObjects: Boolean
        set(value) {
            asJ.isCheckFetchedObjects = value
        }
        get() = asJ.isCheckFetchedObjects

    /**
     * @see FetchCommand.setRemoveDeletedRefs
     * @see FetchCommand.isRemoveDeletedRefs
     */
    var removeDeletedRefs: Boolean
        set(value) {
            asJ.isRemoveDeletedRefs = value
        }
        get() = asJ.isRemoveDeletedRefs

    /**
     * @see FetchCommand.getProgressMonitor
     * @see FetchCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor
        set(value) {
            asJ.progressMonitor = value
        }
        get() = asJ.progressMonitor

    /**
     * @see FetchCommand.setRefSpecs
     * @see FetchCommand.getRefSpecs
     */
    var refSpecs: List<RefSpec> by asJ::refSpecs

    /**
     * @see FetchCommand.setRefSpecs
     */
    fun setRefSpecs(vararg specs: String) {
        asJ.setRefSpecs(*specs)
    }

    /**
     * @see FetchCommand.setRefSpecs
     */
    fun setRefSpecs(vararg specs: RefSpec) {
        asJ.setRefSpecs(*specs)
    }

    /**
     * @see FetchCommand.setDryRun
     * @see FetchCommand.isDryRun
     */
    var dryRun: Boolean
        set(value) {
            asJ.isDryRun = value
        }
        get() = asJ.isDryRun

    /**
     * @see FetchCommand.setThin
     * @see FetchCommand.isThin
     */
    var thin: Boolean
        set(value) {
            asJ.isThin = value
        }
        get() = asJ.isThin

    /**
     * @see FetchCommand.setTagOpt
     */
    var tagOption: TagOpt? = null
        set(value) {
            field = value.apply(asJ::setTagOpt)
        }

    /**
     * @see FetchCommand.setCallback
     */
    var callback: FetchCommand.Callback? = null
        set(value) {
            field = value.apply(asJ::setCallback)
        }

    /**
     * @see FetchCommand.setForceUpdate
     * @see FetchCommand.isForceUpdate
     */
    var forceUpdate: Boolean
        set(value) {
            asJ.isForceUpdate = value
        }
        get() = asJ.isForceUpdate
}