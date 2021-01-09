package com.github.syari.kgit

import org.eclipse.jgit.api.AddCommand
import org.eclipse.jgit.dircache.DirCache
import org.eclipse.jgit.treewalk.WorkingTreeIterator

/**
 * @see AddCommand
 */
class KAddCommand(private val asJ: AddCommand) {
    /**
     * @see AddCommand.call
     */
    fun call(): DirCache? = asJ.call()

    /**
     * @see AddCommand.addFilepattern
     */
    fun addFilepattern(filepattern: String) {
        asJ.addFilepattern(filepattern)
    }

    /**
     * @see AddCommand.setWorkingTreeIterator
     */
    var workingTreeIterator: WorkingTreeIterator? = null
        set(value) {
            field = value.apply(asJ::setWorkingTreeIterator)
        }

    /**
     * @see AddCommand.setUpdate
     */
    var update: Boolean
        set(value) {
            value.apply(asJ::setUpdate)
        }
        get() = asJ.isUpdate
}