@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.CreateBranchCommand
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see CreateBranchCommand
 */
class KCreateBranchCommand(asJ: CreateBranchCommand) : KGitCommand<CreateBranchCommand, Ref>(asJ) {
    /**
     * @see CreateBranchCommand.setName
     */
    fun setName(name: String) {
        asJ.setName(name)
    }

    /**
     * @see CreateBranchCommand.setForce
     */
    fun setForce(force: Boolean) {
        asJ.setForce(force)
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

    fun setUpstreamMode(mode: CreateBranchCommand.SetupUpstreamMode) {
        asJ.setUpstreamMode(mode)
    }
}
