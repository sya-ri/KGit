@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.RebaseCommand
import org.eclipse.jgit.api.RebaseResult
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.merge.MergeStrategy
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see RebaseCommand
 */
class KRebaseCommand(asJ: RebaseCommand) : KGitCommand<RebaseCommand, RebaseResult>(asJ) {
    /**
     * @see RebaseCommand.tryFastForward
     */
    fun tryFastForward(newCommit: RevCommit) {
        asJ.tryFastForward(newCommit)
    }

    /**
     * @see RebaseCommand.setUpstream
     */
    fun setUpstream(upstream: RevCommit) {
        asJ.setUpstream(upstream)
    }

    /**
     * @see RebaseCommand.setUpstream
     */
    fun setUpstream(upstream: AnyObjectId) {
        asJ.setUpstream(upstream)
    }

    /**
     * @see RebaseCommand.setUpstream
     */
    fun setUpstream(upstream: String) {
        asJ.setUpstream(upstream)
    }

    /**
     * @see RebaseCommand.setUpstreamName
     */
    fun setUpstreamName(upstreamName: String?) {
        asJ.setUpstreamName(upstreamName)
    }

    /**
     * @see RebaseCommand.setOperation
     */
    fun setOperation(operation: RebaseCommand.Operation) {
        asJ.setOperation(operation)
    }

    /**
     * @see RebaseCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }

    /**
     * @see RebaseCommand.runInteractively
     */
    fun runInteractively(handler: RebaseCommand.InteractiveHandler) {
        asJ.runInteractively(handler)
    }

    /**
     * @see RebaseCommand.runInteractively
     */
    fun runInteractively(handler: RebaseCommand.InteractiveHandler, stopAfterRebaseInteractiveInitialization: Boolean) {
        asJ.runInteractively(handler, stopAfterRebaseInteractiveInitialization)
    }

    /**
     * @see RebaseCommand.setStrategy
     */
    fun setStrategy(strategy: MergeStrategy) {
        asJ.setStrategy(strategy)
    }

    /**
     * @see RebaseCommand.setPreserveMerges
     */
    fun setPreserveMerges(preserve: Boolean) {
        asJ.setPreserveMerges(preserve)
    }
}
