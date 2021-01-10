package com.github.syari.kgit

import org.eclipse.jgit.api.MergeCommand
import org.eclipse.jgit.api.PullCommand
import org.eclipse.jgit.api.PullResult
import org.eclipse.jgit.lib.BranchConfig.BranchRebaseMode
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.SubmoduleConfig
import org.eclipse.jgit.merge.MergeStrategy
import org.eclipse.jgit.transport.TagOpt

/**
 * @see PullCommand
 */
class KPullCommand(asJ: PullCommand): KGitCommand<PullCommand, PullResult>(asJ) {
    /**
     * @see PullCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }

    /**
     * @see PullCommand.setRebase
     */
    fun setRebase(useRebase: Boolean) {
        asJ.setRebase(useRebase)
    }

    /**
     * @see PullCommand.setRebase
     */
    fun setRebase(rebaseMode: BranchRebaseMode?) {
        asJ.setRebase(rebaseMode)
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
    fun setStrategy(strategy: MergeStrategy) {
        asJ.setStrategy(strategy)
    }

    /**
     * @see PullCommand.setTagOpt
     */
    fun setTagOpt(tagOpt: TagOpt) {
        asJ.setTagOpt(tagOpt)
    }

    /**
     * @see PullCommand.setFastForward
     */
    fun setFastForward(fastForward: MergeCommand.FastForwardMode?) {
        asJ.setFastForward(fastForward)
    }

    /**
     * @see PullCommand.setRecurseSubmodules
     */
    fun setRecurseSubmodules(recurseSubmodules: SubmoduleConfig.FetchRecurseSubmodulesMode?) {
        asJ.setRecurseSubmodules(recurseSubmodules)
    }
}