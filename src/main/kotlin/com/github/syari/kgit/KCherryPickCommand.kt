@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.CherryPickCommand
import org.eclipse.jgit.api.CherryPickResult
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.merge.MergeStrategy

/**
 * @see CherryPickCommand
 */
class KCherryPickCommand(asJ: CherryPickCommand): KGitCommand<CherryPickCommand, CherryPickResult>(asJ) {
    /**
     * @see CherryPickCommand.include
     */
    fun include(commit: Ref) {
        asJ.include(commit)
    }

    /**
     * @see CherryPickCommand.include
     */
    fun include(commit: AnyObjectId) {
        asJ.include(commit)
    }

    /**
     * @see CherryPickCommand.include
     */
    fun include(name: String, commit: AnyObjectId) {
        asJ.include(name, commit)
    }

    /**
     * @see CherryPickCommand.setOurCommitName
     */
    fun setOurCommitName(ourCommitName: String?) {
        asJ.setOurCommitName(ourCommitName)
    }

    /**
     * @see CherryPickCommand.setReflogPrefix
     */
    fun setReflogPrefix(prefix: String?) {
        asJ.setReflogPrefix(prefix)
    }

    /**
     * @see CherryPickCommand.setStrategy
     */
    fun setStrategy(strategy: MergeStrategy) {
        asJ.setStrategy(strategy)
    }

    /**
     * @see CherryPickCommand.setMainlineParentNumber
     */
    fun setMainlineParentNumber(mainlineParentNumber: Int) {
        asJ.setMainlineParentNumber(mainlineParentNumber)
    }

    /**
     * @see CherryPickCommand.setNoCommit
     */
    fun setNoCommit(noCommit: Boolean) {
        asJ.setNoCommit(noCommit)
    }

    /**
     * @see CherryPickCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }
}