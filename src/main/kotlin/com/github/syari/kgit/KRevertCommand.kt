package com.github.syari.kgit

import org.eclipse.jgit.api.MergeResult
import org.eclipse.jgit.api.RevertCommand
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.merge.MergeStrategy
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see RevertCommand
 */
class KRevertCommand(private val asJ: RevertCommand) {
    /**
     * @see RevertCommand.call
     */
    fun call(): RevCommit? = asJ.call()

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
    var ourCommitName: String? = null
        set(value) {
            field = value.apply(asJ::setOurCommitName)
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
    var strategy: MergeStrategy = MergeStrategy.RECURSIVE
        set(value) {
            field = value.apply(asJ::setStrategy)
        }

    /**
     * @see RevertCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor? = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
        }
}