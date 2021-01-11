@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.SubmoduleDeinitCommand
import org.eclipse.jgit.api.SubmoduleDeinitResult

/**
 * @see SubmoduleDeinitCommand
 */
class KSubmoduleDeinitCommand(asJ: SubmoduleDeinitCommand): KGitCommand<SubmoduleDeinitCommand, Collection<SubmoduleDeinitResult>>(asJ) {
    /**
     * @see SubmoduleDeinitCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }

    /**
     * @see SubmoduleDeinitCommand.setForce
     */
    fun setForce(force: Boolean) {
        asJ.setForce(force)
    }
}