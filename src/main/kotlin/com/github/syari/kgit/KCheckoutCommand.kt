package com.github.syari.kgit

import org.eclipse.jgit.api.CheckoutCommand
import org.eclipse.jgit.api.CheckoutResult
import org.eclipse.jgit.api.CreateBranchCommand
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see CheckoutCommand
 */
class KCheckoutCommand(private val asJ: CheckoutCommand) {
    /**
     * @see CheckoutCommand.call
     */
    fun call(): Ref? = asJ.call()

    /**
     * @see CheckoutCommand.setProgressMonitor
     */
    var progressMonitor: ProgressMonitor = NullProgressMonitor.INSTANCE
        set(value) {
            field = value.apply(asJ::setProgressMonitor)
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
    var isAllPaths: Boolean = false
        set(value) {
            field = value.apply(asJ::setAllPaths)
        }

    /**
     * @see CheckoutCommand.setName
     */
    var name: String? = null
        set(value) {
            field = value.apply(asJ::setName)
        }

    /**
     * @see CheckoutCommand.setCreateBranch
     */
    var isCreateBranch: Boolean = false
        set(value) {
            field = value.apply(asJ::setCreateBranch)
        }

    /**
     * @see CheckoutCommand.setOrphan
     */
    var isOrphan: Boolean = false
        set(value) {
            field = value.apply(asJ::setOrphan)
        }

    /**
     * @see CheckoutCommand.setForceRefUpdate
     */
    var isForceRefUpdate: Boolean = false
        set(value) {
            field = value.apply(asJ::setForceRefUpdate)
        }

    /**
     * @see CheckoutCommand.setForced
     */
    var isForced: Boolean = false
        set(value) {
            field = value.apply(asJ::setForced)
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
    var upstreamMode: CreateBranchCommand.SetupUpstreamMode? = null
        set(value) {
            field = value.apply(asJ::setUpstreamMode)
        }

    /**
     * @see CheckoutCommand.setStage
     */
    var stage: CheckoutCommand.Stage? = null
        set(value) {
            field = value.apply(asJ::setStage)
        }

    /**
     * @see CheckoutCommand.getResult
     */
    val result: CheckoutResult by asJ::result
}