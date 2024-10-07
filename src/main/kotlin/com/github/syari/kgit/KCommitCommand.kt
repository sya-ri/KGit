@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.github.syari.kgit

import org.eclipse.jgit.api.CommitCommand
import org.eclipse.jgit.lib.CommitConfig
import org.eclipse.jgit.lib.GpgConfig
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.lib.Signer
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.transport.CredentialsProvider
import java.io.PrintStream

/**
 * @see CommitCommand
 */
class KCommitCommand(asJ: CommitCommand) : KGitCommand<CommitCommand, RevCommit>(asJ) {
    /**
     * @see CommitCommand.setMessage
     * @see CommitCommand.getMessage
     */
    var message: String? by asJ::message

    /**
     * @see CommitCommand.setCleanupMode
     */
    fun setCleanupMode(mode: CommitConfig.CleanupMode) {
        asJ.setCleanupMode(mode)
    }

    /**
     * @see CommitCommand.setDefaultClean
     */
    fun setDefaultClean(strip: Boolean) {
        asJ.setDefaultClean(strip)
    }

    /**
     * @see CommitCommand.setCommentCharacter
     */
    fun setCommentCharacter(commentChar: Char) {
        asJ.setCommentCharacter(commentChar)
    }

    /**
     * @see CommitCommand.setAllowEmpty
     */
    fun setAllowEmpty(allowEmpty: Boolean) {
        asJ.setAllowEmpty(allowEmpty)
    }

    /**
     * @see CommitCommand.setCommitter
     * @see CommitCommand.getCommitter
     */
    var committer: PersonIdent? by asJ::committer

    /**
     * @param name [PersonIdent.name]
     * @param email [PersonIdent.emailAddress]
     */
    fun setCommitter(name: String, email: String) {
        committer = PersonIdent(name, email)
    }

    /**
     * @see CommitCommand.setAuthor
     * @see CommitCommand.getAuthor
     */
    var author: PersonIdent? by asJ::author

    /**
     * @param name [PersonIdent.name]
     * @param email [PersonIdent.emailAddress]
     */
    fun setAuthor(name: String, email: String) {
        author = PersonIdent(name, email)
    }

    /**
     * @see CommitCommand.setAll
     */
    fun setAll(all: Boolean) {
        asJ.setAll(all)
    }

    /**
     * @see CommitCommand.setAmend
     */
    fun setAmend(amend: Boolean) {
        asJ.setAmend(amend)
    }

    /**
     * @see CommitCommand.setOnly
     */
    fun setOnly(only: String) {
        asJ.setOnly(only)
    }

    /**
     * @see CommitCommand.setInsertChangeId
     */
    fun setInsertChangeId(insertChangeId: Boolean) {
        asJ.setInsertChangeId(insertChangeId)
    }

    /**
     * @see CommitCommand.setReflogComment
     */
    fun setReflogComment(reflogComment: String?) {
        asJ.setReflogComment(reflogComment)
    }

    /**
     * @see CommitCommand.setNoVerify
     */
    fun setNoVerify(noVerify: Boolean) {
        asJ.setNoVerify(noVerify)
    }

    /**
     * @see CommitCommand.setHookOutputStream
     */
    fun setHookOutputStream(hookStdOut: PrintStream?) {
        asJ.setHookOutputStream(hookStdOut)
    }

    /**
     * @see CommitCommand.setHookErrorStream
     */
    fun setHookErrorStream(hookStdErr: PrintStream?) {
        asJ.setHookErrorStream(hookStdErr)
    }

    /**
     * @see CommitCommand.setHookOutputStream
     */
    fun setHookOutputStream(hookName: String, hookStdOut: PrintStream?) {
        asJ.setHookOutputStream(hookName, hookStdOut)
    }

    /**
     * @see CommitCommand.setHookErrorStream
     */
    fun setHookErrorStream(hookName: String, hookStdErr: PrintStream?) {
        asJ.setHookOutputStream(hookName, hookStdErr)
    }

    /**
     * @see CommitCommand.setSigningKey
     */
    fun setSigningKey(signingKey: String?) {
        asJ.setSigningKey(signingKey)
    }

    /**
     * @see CommitCommand.setSign
     */
    fun setSign(sign: Boolean?) {
        asJ.setSign(sign)
    }

    /**
     * @see CommitCommand.setSigner
     */
    fun setSigner(signer: Signer?) {
        asJ.setSigner(signer)
    }

    /**
     * @see CommitCommand.setGpgConfig
     */
    fun setGpgConfig(config: GpgConfig?) {
        asJ.setGpgConfig(config)
    }

    /**
     * @see CommitCommand.setCredentialsProvider
     */
    fun setCredentialsProvider(credentialsProvider: CredentialsProvider?) {
        asJ.setCredentialsProvider(credentialsProvider)
    }
}
