package com.github.syari.kgit

import org.eclipse.jgit.api.StashApplyCommand
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.merge.MergeStrategy

/**
 * @see StashApplyCommand
 */
class KStashApplyCommand(asJ: StashApplyCommand): KGitCommand<StashApplyCommand, ObjectId>(asJ) {
    /**
     * @see StashApplyCommand.setStashRef
     */
    fun setStashRef(stashRef: String?) {
        asJ.setStashRef(stashRef)
    }

    /**
     * @see StashApplyCommand.ignoreRepositoryState
     */
    fun ignoreRepositoryState(willIgnoreRepositoryState: Boolean) {
        asJ.ignoreRepositoryState(willIgnoreRepositoryState)
    }

    /**
     * @see StashApplyCommand.setRestoreIndex
     */
    fun setRestoreIndex(restoreIndex: Boolean) {
        asJ.setRestoreIndex(restoreIndex)
    }

    /**
     * @see StashApplyCommand.setStrategy
     */
    fun setStrategy(strategy: MergeStrategy) {
        asJ.setStrategy(strategy)
    }

    /**
     * @see StashApplyCommand.setRestoreUntracked
     */
    fun setRestoreUntracked(restoreUntracked: Boolean) {
        asJ.setRestoreUntracked(restoreUntracked)
    }
}