package com.github.syari.kgit.api

import org.eclipse.jgit.api.MergeCommand
import org.eclipse.jgit.api.MergeResult
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.merge.MergeStrategy

class KMergeCommand(private val asJ: MergeCommand) {
    /**
     * @see MergeCommand.call
     */
    fun call(): MergeResult = asJ.call()

    /**
     * @see MergeCommand.setStrategy
     */
    var mergeStrategy: MergeStrategy = MergeStrategy.RECURSIVE
        set(value) {
            field = value.apply(asJ::setStrategy)
        }

    /**
     * @see MergeCommand.include
     */
    fun include(commit: Ref) {
        asJ.include(commit)
    }

    /**
     * @see MergeCommand.include
     */
    fun include(commit: AnyObjectId) {
        asJ.include(commit)
    }

    /**
     * @see MergeCommand.include
     */
    fun include(name: String, commit: AnyObjectId) {
        asJ.include(name, commit)
    }

    /**
     * @see MergeCommand.setStrategy
     */
    var squash: Boolean? = null
        set(value) {
            value?.let { field = it.apply(asJ::setSquash) }
        }

    /**
     * @see MergeCommand.setStrategy
     */
    var fastForward: MergeCommand.FastForwardMode? = null
        set(value) {
            field = value.apply(asJ::setFastForward)
        }

    /**
     * @see MergeCommand.setCommit
     */
    var commit: Boolean? = null
        set(value) {
            value?.let { field = it.apply(asJ::setCommit) }
        }

    /**
     * @see MergeCommand.setMessage
     */
    var message: String? = null
        set(value) {
            field = value.apply(asJ::setMessage)
        }

    /**
     * @see MergeCommand.setInsertChangeId
     */
    var insertChangeId: Boolean = false
        set(value) {
            field = value.apply(asJ::setInsertChangeId)
        }

    /**
     * @see MergeCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
        }
}