@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.MergeResult
import org.eclipse.jgit.api.RevertCommand
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.merge.MergeStrategy
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see RevertCommand
 */
class KRevertCommand(asJ: RevertCommand) : KGitCommand<RevertCommand, RevCommit?>(asJ) {
    /**
     * @see RevertCommand.include
     */
    fun include(commit: Ref) {
        asJ.include(commit)
    }

    /**
     * @see RevertCommand.include
     */
    fun include(commit: AnyObjectId) {
        asJ.include(commit)
    }

    /**
     * @see RevertCommand.include
     */
    fun include(name: String, commit: AnyObjectId) {
        asJ.include(name, commit)
    }

    /**
     * @see RevertCommand.setOurCommitName
     */
    fun setOurCommitName(ourCommitName: String?) {
        asJ.setOurCommitName(ourCommitName)
    }

    /**
     * @see RevertCommand.getRevertedRefs
     */
    val revertedRefs: List<Ref> by asJ::revertedRefs

    /**
     * @see RevertCommand.getFailingResult
     */
    val failingResult: MergeResult? by asJ::failingResult

    /**
     * @see RevertCommand.getUnmergedPaths
     */
    val unmergedPaths: List<String>? by asJ::unmergedPaths

    /**
     * @see RevertCommand.setStrategy
     */
    fun setStrategy(strategy: MergeStrategy) {
        asJ.setStrategy(strategy)
    }

    /**
     * @see RevertCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }

    /**
     * @see RevertCommand.setInsertChangeId
     */
    fun setInsertChangeId(insertChangeId: Boolean) {
        asJ.setInsertChangeId(insertChangeId)
    }
}
