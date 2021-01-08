package com.github.syari.kgit

import org.eclipse.jgit.api.MergeCommand
import org.eclipse.jgit.api.PullCommand
import org.eclipse.jgit.api.PullResult
import org.eclipse.jgit.lib.BranchConfig.BranchRebaseMode
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.SubmoduleConfig
import org.eclipse.jgit.merge.MergeStrategy
import org.eclipse.jgit.transport.TagOpt

/**
 * @see PullCommand
 */
class KPullCommand(private val asJ: PullCommand) {
    /**
     * @see PullCommand.call
     */
    fun call(): PullResult = asJ.call()

    /**
     * @see PullCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
        }


    /**
     * @see PullCommand.setRebase
     */
    fun setRebase(useRebase: Boolean) {
        rebase = if (useRebase) BranchRebaseMode.REBASE else BranchRebaseMode.NONE
    }

    /**
     * @see PullCommand.setRebase
     */
    var rebase: BranchRebaseMode? = null
        set(value) {
            field = value.apply(asJ::setRebase)
        }

    /**
     * @see PullCommand.remote
     */
    var remote: String? by asJ::remote

    /**
     * @see PullCommand.remoteBranchName
     */
    var remoteBranchName: String? by asJ::remoteBranchName

    /**
     * @see PullCommand.setStrategy
     */
    var strategy: MergeStrategy = MergeStrategy.RECURSIVE
        set(value) {
            field = value.apply(asJ::setStrategy)
        }

    /**
     * @see PullCommand.setTagOpt
     */
    var tagOpt: TagOpt? = null
        set(value) {
            field = value.apply(asJ::setTagOpt)
        }

    /**
     * @see PullCommand.setFastForward
     */
    var fastForward: MergeCommand.FastForwardMode? = null
        set(value) {
            field = value.apply(asJ::setFastForward)
        }

    /**
     * @see PullCommand.setRecurseSubmodules
     */
    var recurseSubmodules: SubmoduleConfig.FetchRecurseSubmodulesMode? = null
        set(value) {
            field = value.apply(asJ::setRecurseSubmodules)
        }
}