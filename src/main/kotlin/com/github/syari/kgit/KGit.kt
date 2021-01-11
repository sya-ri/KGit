package com.github.syari.kgit

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.util.FS
import java.io.File

/**
 * @see Git
 */
class KGit(asJ: Git): AutoCloseable, KWrapper<Git>(asJ) {
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
        inline fun cloneRepository(action: KCloneCommand.() -> Unit = {}) = KCloneCommand().apply(action).callAsK()

        /**
         * @see Git.lsRemoteRepository
         */
        inline fun lsRemoteRepository(action: KLsRemoteCommand.() -> Unit = {}) = KLsRemoteCommand().apply(action).call()

        /**
         * @see Git.init
         */
        inline fun init(action: KInitCommand.() -> Unit = {}) = KInitCommand().apply(action).callAsK()

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

    /**
     * @see Git.fetch
     */
    fun fetch(action: KFetchCommand.() -> Unit = {}) = KFetchCommand(asJ.fetch()).apply(action).call()

    /**
     * @see Git.push
     */
    fun push(action: KPushCommand.() -> Unit = {}) = KPushCommand(asJ.push()).apply(action).call()

    /**
     * @see Git.cherryPick
     */
    fun cherryPick(action: KCherryPickCommand.() -> Unit = {}) = KCherryPickCommand(asJ.cherryPick()).apply(action).call()

    /**
     * @see Git.revert
     */
    fun revert(action: KRevertCommand.() -> Unit = {}) = KRevertCommand(asJ.revert()).apply(action).call()

    /**
     * @see Git.rebase
     */
    fun rebase(action: KRebaseCommand.() -> Unit = {}) = KRebaseCommand(asJ.rebase()).apply(action).call()

    /**
     * @see Git.rm
     */
    fun rm(action: KRmCommand.() -> Unit = {}) = KRmCommand(asJ.rm()).apply(action).call()

    /**
     * @see Git.checkout
     */
    fun checkout(action: KCheckoutCommand.() -> Unit = {}) = KCheckoutCommand(asJ.checkout()).apply(action).call()

    /**
     * @see Git.reset
     */
    fun reset(action: KResetCommand.() -> Unit = {}) = KResetCommand(asJ.reset()).apply(action).call()

    /**
     * @see Git.status
     */
    fun status(action: KStatusCommand.() -> Unit = {}) = KStatusCommand(asJ.status()).apply(action).call()

    /**
     * @see Git.archive
     */
    fun archive(action: KArchiveCommand.() -> Unit = {}) = KArchiveCommand(asJ.archive()).apply(action).call()

    /**
     * @see Git.notesAdd
     */
    fun notesAdd(action: KAddNoteCommand.() -> Unit = {}) = KAddNoteCommand(asJ.notesAdd()).apply(action).call()

    /**
     * @see Git.notesRemove
     */
    fun notesRemove(action: KRemoveNoteCommand.() -> Unit = {}) = KRemoveNoteCommand(asJ.notesRemove()).apply(action).call()

    /**
     * @see Git.notesList
     */
    fun notesList(action: KListNotesCommand.() -> Unit = {}) = KListNotesCommand(asJ.notesList()).apply(action).call()

    /**
     * @see Git.notesShow
     */
    fun notesShow(action: KShowNoteCommand.() -> Unit = {}) = KShowNoteCommand(asJ.notesShow()).apply(action).call()

    /**
     * @see Git.lsRemote
     */
    fun lsRemote(action: KLsRemoteCommand.() -> Unit = {}) = KLsRemoteCommand(asJ.lsRemote()).apply(action).call()

    /**
     * @see Git.clean
     */
    fun clean(action: KCleanCommand.() -> Unit = {}) = KCleanCommand(asJ.clean()).apply(action).call()

    /**
     * @see Git.blame
     */
    fun blame(action: KBlameCommand.() -> Unit = {}) = KBlameCommand(asJ.blame()).apply(action).call()

    /**
     * @see Git.reflog
     */
    fun reflog(action: KReflogCommand.() -> Unit = {}) = KReflogCommand(asJ.reflog()).apply(action).call()

    /**
     * @see Git.diff
     */
    fun diff(action: KDiffCommand.() -> Unit = {}) = KDiffCommand(asJ.diff()).apply(action).call()

    /**
     * @see Git.tagDelete
     */
    fun tagDelete(action: KDeleteTagCommand.() -> Unit = {}) = KDeleteTagCommand(asJ.tagDelete()).apply(action).call()

    /**
     * @see Git.submoduleAdd
     */
    fun submoduleAdd(action: KSubmoduleAddCommand.() -> Unit = {}) = KSubmoduleAddCommand(asJ.submoduleAdd()).apply(action).call()

    /**
     * @see Git.submoduleInit
     */
    fun submoduleInit(action: KSubmoduleInitCommand.() -> Unit = {}) = KSubmoduleInitCommand(asJ.submoduleInit()).apply(action).call()

    /**
     * @see Git.submoduleDeinit
     */
    fun submoduleDeinit(action: KSubmoduleDeinitCommand.() -> Unit = {}) = KSubmoduleDeinitCommand(asJ.submoduleDeinit()).apply(action).call()

    /**
     * @see Git.submoduleStatus
     */
    fun submoduleStatus(action: KSubmoduleStatusCommand.() -> Unit = {}) = KSubmoduleStatusCommand(asJ.submoduleStatus()).apply(action).call()

    /**
     * @see Git.getRepository
     */
    val repository: Repository by asJ::repository
}