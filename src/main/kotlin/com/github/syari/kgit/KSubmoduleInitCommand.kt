package com.github.syari.kgit

import org.eclipse.jgit.api.SubmoduleInitCommand

/**
 * @see SubmoduleInitCommand
 */
class KSubmoduleInitCommand(asJ: SubmoduleInitCommand): KGitCommand<SubmoduleInitCommand, Collection<String>>(asJ) {
    /**
     * @see SubmoduleInitCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }
}