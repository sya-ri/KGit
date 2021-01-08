package com.github.syari.kgit

import com.github.syari.kgit.api.KCloneCommand
import com.github.syari.kgit.api.KCommitCommand
import com.github.syari.kgit.api.KInitCommand
import com.github.syari.kgit.api.KLogCommand
import com.github.syari.kgit.api.KLsRemoteCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.util.FS
import java.io.File

/**
 * @see Git
 */
class KGit(private val asJ: Git) {
    companion object {
        /**
         * @see Git.open
         */
        fun open(dir: File, fs: FS = FS.DETECTED) = KGit(Git.open(dir, fs))

        /**
         * @see Git.open
         */
        fun wrap(repo: Repository) = KGit(repo)

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

        /**
         * @see Git.shutdown
         */
        fun shutdown() = Git.shutdown()
    }

    constructor(repo: Repository): this(Git(repo))

    /**
     * @see Git.commit
     */
    fun commit(action: KCommitCommand.() -> Unit = {}) = KCommitCommand(asJ.commit()).apply(action).call()

    /**
     * @see Git.log
     */
    fun log(action: KLogCommand.() -> Unit = {}) = KLogCommand(asJ.log()).apply(action).call()
}