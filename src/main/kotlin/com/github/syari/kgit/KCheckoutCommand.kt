@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.CheckoutCommand
import org.eclipse.jgit.api.CheckoutResult
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see CheckoutCommand
 */
class KCheckoutCommand(asJ: CheckoutCommand) : KGitCommand<CheckoutCommand, Ref?>(asJ) {
    /**
     * @see CheckoutCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }

    /**
     * @see CheckoutCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }

    /**
     * @see CheckoutCommand.addPaths
     */
    fun addPaths(p: List<String>) {
        asJ.addPaths(p)
    }

    /**
     * @see CheckoutCommand.setAllPaths
     */
    fun setAllPaths(all: Boolean) {
        asJ.setAllPaths(all)
    }

    /**
     * @see CheckoutCommand.setName
     */
    fun setName(name: String?) {
        asJ.setName(name)
    }

    /**
     * @see CheckoutCommand.setCreateBranch
     */
    fun setCreateBranch(createBranch: Boolean) {
        asJ.setCreateBranch(createBranch)
    }

    /**
     * @see CheckoutCommand.setOrphan
     */
    fun setOrphan(orphan: Boolean) {
        asJ.setOrphan(orphan)
    }

    /**
     * @see CheckoutCommand.setForceRefUpdate
     */
    fun setForceRefUpdate(forceRefUpdate: Boolean) {
        asJ.setForceRefUpdate(forceRefUpdate)
    }

    /**
     * @see CheckoutCommand.setForced
     */
    fun setForced(forced: Boolean) {
        asJ.setForced(forced)
    }

    /**
     * @see CheckoutCommand.setStartPoint
     */
    fun setStartPoint(startPoint: String?) {
        asJ.setStartPoint(startPoint)
    }

    /**
     * @see CheckoutCommand.setStartPoint
     */
    fun setStartPoint(startPoint: RevCommit?) {
        asJ.setStartPoint(startPoint)
    }

    /**
     * @see CheckoutCommand.setUpstreamMode
     */
    fun setUpstreamMode(mode: SetupUpstreamMode?) {
        asJ.setUpstreamMode(mode)
    }

    /**
     * @see CheckoutCommand.setStage
     */
    fun setStage(stage: CheckoutCommand.Stage?) {
        asJ.setStage(stage)
    }

    /**
     * @see CheckoutCommand.getResult
     */
    val result: CheckoutResult by asJ::result
}
