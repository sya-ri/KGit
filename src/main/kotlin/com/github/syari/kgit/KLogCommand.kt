@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.LogCommand
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.filter.RevFilter

/**
 * @see LogCommand
 */
class KLogCommand(asJ: LogCommand) : KGitCommand<LogCommand, Iterable<RevCommit>>(asJ) {
    /**
     * @see LogCommand.add
     */
    fun add(start: AnyObjectId) {
        asJ.add(start)
    }

    /**
     * @see LogCommand.not
     */
    fun not(start: AnyObjectId) {
        asJ.not(start)
    }

    /**
     * @see LogCommand.addRange
     */
    fun addRange(since: AnyObjectId, until: AnyObjectId) {
        asJ.addRange(since, until)
    }

    /**
     * @see LogCommand.all
     */
    fun all() {
        asJ.all()
    }

    /**
     * @see LogCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }

    /**
     * @see LogCommand.excludePath
     */
    fun excludePath(path: String) {
        asJ.excludePath(path)
    }

    /**
     * @see LogCommand.setSkip
     */
    fun setSkip(skip: Int) {
        asJ.setSkip(skip)
    }

    /**
     * @see LogCommand.setMaxCount
     */
    fun setMaxCount(maxCount: Int) {
        asJ.setMaxCount(maxCount)
    }

    /**
     * @see LogCommand.setRevFilter
     */
    fun setRevFilter(aFilter: RevFilter?) {
        asJ.setRevFilter(aFilter)
    }
}
