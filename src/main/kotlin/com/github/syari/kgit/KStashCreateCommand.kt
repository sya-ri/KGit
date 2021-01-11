package com.github.syari.kgit

import org.eclipse.jgit.api.StashCreateCommand
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see StashCreateCommand
 */
class KStashCreateCommand(asJ: StashCreateCommand): KGitCommand<StashCreateCommand, RevCommit>(asJ) {
    /**
     * @see StashCreateCommand.setIndexMessage
     */
    fun setIndexMessage(message: String) {
        asJ.setIndexMessage(message)
    }

    /**
     * @see StashCreateCommand.setWorkingDirectoryMessage
     */
    fun setWorkingDirectoryMessage(message: String) {
        asJ.setWorkingDirectoryMessage(message)
    }

    /**
     * @see StashCreateCommand.setPerson
     */
    fun setPerson(person: PersonIdent?) {
        asJ.setPerson(person)
    }

    /**
     * @see StashCreateCommand.setRef
     */
    fun setRef(ref: String?) {
        asJ.setRef(ref)
    }

    /**
     * @see StashCreateCommand.setIncludeUntracked
     */
    fun setIncludeUntracked(includeUntracked: Boolean) {
        asJ.setIncludeUntracked(includeUntracked)
    }
}