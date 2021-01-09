package com.github.syari.kgit

import org.eclipse.jgit.api.CreateBranchCommand
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see CreateBranchCommand
 */
class KCreateBranchCommand(private val asJ: CreateBranchCommand) {
    /**
     * @see CreateBranchCommand.call
     */
    fun call(): Ref = asJ.call()

    /**
     * @see CreateBranchCommand.setName
     */
    var name: String? = null
        set(value) {
            field = value.apply(asJ::setName)
        }

    /**
     * @see CreateBranchCommand.setForce
     */
    var force: Boolean = false
        set(value) {
            field = value.apply(asJ::setForce)
        }

    /**
     * @see CreateBranchCommand.setStartPoint
     */
    fun setStartPoint(startPoint: String?) {
        asJ.setStartPoint(startPoint)
    }

    /**
     * @see CreateBranchCommand.setStartPoint
     */
    fun setStartPoint(startPoint: RevCommit?) {
        asJ.setStartPoint(startPoint)
    }

    /**
     * @see CreateBranchCommand.setUpstreamMode
     */
    var upstreamMode: CreateBranchCommand.SetupUpstreamMode? = null
        set(value) {
            field = value.apply(asJ::setUpstreamMode)
        }
}