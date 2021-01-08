package com.github.syari.kgit

import org.eclipse.jgit.api.LogCommand
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.filter.RevFilter

/**
 * @see LogCommand
 */
class KLogCommand(private val asJ: LogCommand) {
    /**
     * @see LogCommand.call
     */
    fun call(): Iterable<RevCommit> = asJ.call()

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
    var skip: Int = -1
        set(value) {
            field = value.apply(asJ::setSkip)
        }

    /**
     * @see LogCommand.setMaxCount
     */
    var maxCount: Int = -1
        set(value) {
            field = value.apply(asJ::setMaxCount)
        }

    /**
     * @see LogCommand.setRevFilter
     */
    var revFilter: RevFilter? = null
        set(value) {
            field = value.apply(asJ::setRevFilter)
        }
}