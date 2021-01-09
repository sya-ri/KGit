package com.github.syari.kgit

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.util.FS
import java.io.File

/**
 * @see Git
 */
class KGit(private val asJ: Git): AutoCloseable {
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
     * @see Git.close
     */
    override fun close() = asJ.close()

    /**
     * @see Git.commit
     */
    fun commit(action: KCommitCommand.() -> Unit = {}) = KCommitCommand(asJ.commit()).apply(action).call()

    /**
     * @see Git.log
     */
    fun log(action: KLogCommand.() -> Unit = {}) = KLogCommand(asJ.log()).apply(action).call()

    /**
     * @see Git.merge
     */
    fun merge(action: KMergeCommand.() -> Unit = {}) = KMergeCommand(asJ.merge()).apply(action).call()

    /**
     * @see Git.pull
     */
    fun pull(action: KPullCommand.() -> Unit = {}) = KPullCommand(asJ.pull()).apply(action).call()

    /**
     * @see Git.branchCreate
     */
    fun branchCreate(action: KCreateBranchCommand.() -> Unit = {}) = KCreateBranchCommand(asJ.branchCreate()).apply(action).call()

    /**
     * @see Git.branchDelete
     */
    fun branchDelete(action: KDeleteBranchCommand.() -> Unit = {}) = KDeleteBranchCommand(asJ.branchDelete()).apply(action).call()

    /**
     * @see Git.branchList
     */
    fun branchList(action: KListBranchCommand.() -> Unit = {}) = KListBranchCommand(asJ.branchList()).apply(action).call()

    /**
     * @see Git.tagList
     */
    fun tagList(action: KListTagCommand.() -> Unit = {}) = KListTagCommand(asJ.tagList()).apply(action).call()

    /**
     * @see Git.branchRename
     */
    fun branchRename(action: KRenameBranchCommand.() -> Unit = {}) = KRenameBranchCommand(asJ.branchRename()).apply(action).call()

    /**
     * @see Git.add
     */
    fun add(action: KAddCommand.() -> Unit = {}) = KAddCommand(asJ.add()).apply(action).call()

    /**
     * @see Git.tag
     */
    fun tag(action: KTagCommand.() -> Unit = {}) = KTagCommand(asJ.tag()).apply(action).call()
}