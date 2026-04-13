@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.MergeCommand
import org.eclipse.jgit.api.MergeCommand.ConflictStyle
import org.eclipse.jgit.api.MergeResult
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.merge.ContentMergeStrategy
import org.eclipse.jgit.merge.MergeStrategy

class KMergeCommand(asJ: MergeCommand) : KGitCommand<MergeCommand, MergeResult>(asJ) {
    /**
     * @see MergeCommand.setStrategy
     */
    fun setStrategy(mergeStrategy: MergeStrategy) {
        asJ.setStrategy(mergeStrategy)
    }

    /**
     * @see MergeCommand.setContentMergeStrategy
     */
    fun setContentMergeStrategy(strategy: ContentMergeStrategy?) {
        asJ.setContentMergeStrategy(strategy)
    }

    /**
     * @see MergeCommand.setConflictStyle
     */
    fun setConflictStyle(conflictStyle: ConflictStyle) {
        asJ.setConflictStyle(conflictStyle)
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
    fun setSquash(squash: Boolean) {
        asJ.setSquash(squash)
    }

    /**
     * @see MergeCommand.setStrategy
     */
    fun setFastForward(fastForward: MergeCommand.FastForwardMode?) {
        asJ.setFastForward(fastForward)
    }

    /**
     * @see MergeCommand.setCommit
     */
    fun setCommit(commit: Boolean) {
        asJ.setCommit(commit)
    }

    /**
     * @see MergeCommand.setMessage
     */
    fun setMessage(message: String?) {
        asJ.setMessage(message)
    }

    /**
     * @see MergeCommand.setInsertChangeId
     */
    fun setInsertChangeId(insertChangeId: Boolean) {
        asJ.setInsertChangeId(insertChangeId)
    }

    /**
     * @see MergeCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }
}
