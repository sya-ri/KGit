package com.github.syari.kgit

import org.eclipse.jgit.api.RebaseCommand
import org.eclipse.jgit.api.RebaseResult
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.merge.MergeStrategy
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see RebaseCommand
 */
class KRebaseCommand(private val asJ: RebaseCommand) {
    /**
     * @see RebaseCommand.call
     */
    fun call(): RebaseResult = asJ.call()

    /**
     * @see RebaseCommand.tryFastForward
     */
    fun tryFastForward(newCommit: RevCommit) {
        asJ.tryFastForward(newCommit)
    }

    /**
     * @see RebaseCommand.setUpstream
     * @see RebaseCommand.setUpstreamName
     */
    fun setUpstream(upstream: RevCommit, upstreamName: String? = null) {
        asJ.setUpstream(upstream)
        upstreamName?.let { asJ.setUpstreamName(it) }
    }

    /**
     * @see RebaseCommand.setUpstream
     * @see RebaseCommand.setUpstreamName
     */
    fun setUpstream(upstream: AnyObjectId, upstreamName: String? = null) {
        asJ.setUpstream(upstream)
        upstreamName?.let { asJ.setUpstreamName(it) }
    }

    /**
     * @see RebaseCommand.setUpstream
     * @see RebaseCommand.setUpstreamName
     */
    fun setUpstream(upstream: String, upstreamName: String? = null) {
        asJ.setUpstream(upstream)
        upstreamName?.let { asJ.setUpstreamName(it) }
    }

    /**
     * @see RebaseCommand.setOperation
     */
    var operation: RebaseCommand.Operation = RebaseCommand.Operation.BEGIN
        set(value) {
            field = value.apply(asJ::setOperation)
        }

    /**
     * @see RebaseCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
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
    var strategy: MergeStrategy = MergeStrategy.RECURSIVE
        set(value) {
            field = value.apply(asJ::setStrategy)
        }

    /**
     * @see RebaseCommand.setPreserveMerges
     */
    var preserveMerges: Boolean = false
        set(value) {
            field = value.apply(asJ::setPreserveMerges)
        }
}