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
    fun setRecurseSubmodules(recurse: SubmoduleConfig.FetchRecurseSubmodulesMode?) {
        asJ.setRecurseSubmodules(recurse)
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
    var isCheckFetchedObjects: Boolean
        set(value) {
            asJ.isCheckFetchedObjects = value
        }
        get() = asJ.isCheckFetchedObjects

    /**
     * @see FetchCommand.setRemoveDeletedRefs
     * @see FetchCommand.isRemoveDeletedRefs
     */
    var isRemoveDeletedRefs: Boolean
        set(value) {
            asJ.isRemoveDeletedRefs = value
        }
        get() = asJ.isRemoveDeletedRefs

    /**
     * @see FetchCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.progressMonitor = monitor
    }

    /**
     * @see FetchCommand.getProgressMonitor
     */
    val progressMonitor: ProgressMonitor by asJ::progressMonitor

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
    var isDryRun: Boolean
        set(value) {
            asJ.isDryRun = value
        }
        get() = asJ.isDryRun

    /**
     * @see FetchCommand.setThin
     * @see FetchCommand.isThin
     */
    var isThin: Boolean
        set(value) {
            asJ.isThin = value
        }
        get() = asJ.isThin

    /**
     * @see FetchCommand.setTagOpt
     */
    fun setTagOpt(tagOpt: TagOpt?) {
        asJ.setTagOpt(tagOpt)
    }

    /**
     * @see FetchCommand.setCallback
     */
    fun setCallback(callback: FetchCommand.Callback) {
        asJ.setCallback(callback)
    }

    /**
     * @see FetchCommand.setForceUpdate
     * @see FetchCommand.isForceUpdate
     */
    var isForceUpdate: Boolean
        set(value) {
            asJ.isForceUpdate = value
        }
        get() = asJ.isForceUpdate
}