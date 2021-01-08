package com.github.syari.kgit

import com.github.syari.kgit.api.KCloneCommand
import org.eclipse.jgit.api.Git

/**
 * @see Git
 */
class KGit(private val asJ: Git) {
    companion object {
        /**
         * @see Git.cloneRepository
         */
        inline fun cloneRepository(action: KCloneCommand.() -> Unit = {}) = KCloneCommand().apply(action).call()
    }
}