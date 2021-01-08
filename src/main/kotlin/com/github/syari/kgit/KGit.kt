package com.github.syari.kgit

import com.github.syari.kgit.api.KCloneCommand
import com.github.syari.kgit.api.KInitCommand
import com.github.syari.kgit.api.KLsRemoteCommand
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

        /**
         * @see Git.lsRemoteRepository
         */
        inline fun lsRemoteRepository(action: KLsRemoteCommand.() -> Unit = {}) = KLsRemoteCommand().apply(action).call()

        /**
         * @see Git.init
         */
        inline fun init(action: KInitCommand.() -> Unit = {}) = KInitCommand().apply(action).call()
    }
}