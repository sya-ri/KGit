package com.github.syari.kgit

import org.eclipse.jgit.api.SubmoduleStatusCommand
import org.eclipse.jgit.submodule.SubmoduleStatus

/**
 * @see SubmoduleStatusCommand
 */
class KSubmoduleStatusCommand(asJ: SubmoduleStatusCommand): KGitCommand<SubmoduleStatusCommand, Map<String, SubmoduleStatus>>(asJ) {
    /**
     * @see SubmoduleStatusCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }
}