@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.AddCommand
import org.eclipse.jgit.dircache.DirCache
import org.eclipse.jgit.treewalk.WorkingTreeIterator

/**
 * @see AddCommand
 */
class KAddCommand(asJ: AddCommand) : KGitCommand<AddCommand, DirCache?>(asJ) {
    /**
     * @see AddCommand.addFilepattern
     */
    fun addFilepattern(filepattern: String) {
        asJ.addFilepattern(filepattern)
    }

    /**
     * @see AddCommand.setWorkingTreeIterator
     */
    fun setWorkingTreeIterator(f: WorkingTreeIterator?) {
        asJ.setWorkingTreeIterator(f)
    }

    /**
     * @see AddCommand.setUpdate
     * @see AddCommand.isUpdate
     */
    var isUpdate: Boolean
        set(value) {
            asJ.isUpdate = value
        }
        get() = asJ.isUpdate

    /**
     * @see AddCommand.setRenormalize
     * @see AddCommand.isRenormalize
     */
    var isRenormalize: Boolean
        set(value) {
            asJ.isRenormalize = value
        }
        get() = asJ.isRenormalize
}
