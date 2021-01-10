package com.github.syari.kgit

import org.eclipse.jgit.api.CherryPickCommand
import org.eclipse.jgit.api.CherryPickResult
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.merge.MergeStrategy

/**
 * @see CherryPickCommand
 */
class KCherryPickCommand(private val asJ: CherryPickCommand) {
    /**
     * @see CherryPickCommand.call
     */
    fun call(): CherryPickResult = asJ.call()

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
    var ourCommitName: String? = null
        set(value) {
            field = value.apply(asJ::setOurCommitName)
        }

    /**
     * @see CherryPickCommand.setReflogPrefix
     */
    var reflogPrefix: String? = "cherry-pick:" //$NON-NLS-1$
        set(value) {
            field = value.apply(asJ::setReflogPrefix)
        }

    /**
     * @see CherryPickCommand.setStrategy
     */
    var strategy: MergeStrategy = MergeStrategy.RECURSIVE
        set(value) {
            field = value.apply(asJ::setStrategy)
        }

    /**
     * @see CherryPickCommand.setMainlineParentNumber
     */
    var mainlineParentNumber: Int? = null
        set(value) {
            value?.let { field = it.apply(asJ::setMainlineParentNumber) }
        }

    /**
     * @see CherryPickCommand.setNoCommit
     */
    var isNoCommit: Boolean = false
        set(value) {
            field = value.apply(asJ::setNoCommit)
        }

    /**
     * @see CherryPickCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor? = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
        }
}