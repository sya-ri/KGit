package com.github.syari.kgit

import org.eclipse.jgit.api.CommitCommand
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.transport.CredentialsProvider
import java.io.PrintStream

/**
 * @see CommitCommand
 */
class KCommitCommand(private val asJ: CommitCommand) {
    /**
     * @see CommitCommand.call
     */
    fun call(): RevCommit = asJ.call()

    /**
     * @see CommitCommand.setMessage
     * @see CommitCommand.getMessage
     */
    var message: String by asJ::message

    /**
     * @see CommitCommand.setAllowEmpty
     */
    var allowEmpty: Boolean? = null
        set(value) {
            value?.let { field = it.apply(asJ::setAllowEmpty) }
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
    var all: Boolean = false
        set(value) {
            field = value.apply(asJ::setAll)
        }

    /**
     * @see CommitCommand.setAmend
     */
    var amend: Boolean = false
        set(value) {
            field = value.apply(asJ::setAmend)
        }

    /**
     * @see CommitCommand.setOnly
     */
    fun addOnly(only: String) {
        asJ.setOnly(only)
    }

    /**
     * @see CommitCommand.setInsertChangeId
     */
    var insertChangeId: Boolean = false
        set(value) {
            field = value.apply(asJ::setInsertChangeId)
        }

    /**
     * @see CommitCommand.setReflogComment
     */
    var reflogComment: String? = null
        set(value) {
            field = value.apply(asJ::setReflogComment)
        }

    /**
     * @see CommitCommand.setNoVerify
     */
    var noVerify: Boolean = false
        set(value) {
            field = value.apply(asJ::setNoVerify)
        }

    /**
     * @see CommitCommand.setHookOutputStream
     */
    fun setHookOutputStream(hookStdOut: PrintStream) {
        asJ.setHookOutputStream(hookStdOut)
    }

    /**
     * @see CommitCommand.setHookErrorStream
     */
    fun setHookErrorStream(hookStdErr: PrintStream) {
        asJ.setHookErrorStream(hookStdErr)
    }

    /**
     * @see CommitCommand.setHookOutputStream
     */
    fun setHookOutputStream(hookName: String, hookStdOut: PrintStream) {
        asJ.setHookOutputStream(hookName, hookStdOut)
    }

    /**
     * @see CommitCommand.setHookErrorStream
     */
    fun setHookErrorStream(hookName: String, hookStdErr: PrintStream) {
        asJ.setHookOutputStream(hookName, hookStdErr)
    }

    /**
     * @see CommitCommand.setSigningKey
     */
    var signingKey: String? = null
        set(value) {
            field = value.apply(asJ::setSigningKey)
        }

    /**
     * @see CommitCommand.setSign
     */
    var sign: Boolean? = null
        set(value) {
            field = value.apply(asJ::setSign)
        }

    /**
     * @see CommitCommand.setCredentialsProvider
     */
    var credentialsProvider: CredentialsProvider? = null
        set(value) {
            field = value.apply(asJ::setCredentialsProvider)
        }
}