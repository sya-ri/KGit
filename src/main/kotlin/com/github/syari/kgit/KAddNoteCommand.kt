@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.AddNoteCommand
import org.eclipse.jgit.notes.Note
import org.eclipse.jgit.revwalk.RevObject

/**
 * @see AddNoteCommand
 */
class KAddNoteCommand(asJ: AddNoteCommand) : KGitCommand<AddNoteCommand, Note?>(asJ) {
    /**
     * @see AddNoteCommand.setObjectId
     */
    fun setObjectId(id: RevObject?) {
        asJ.setObjectId(id)
    }

    /**
     * @see AddNoteCommand.setMessage
     */
    fun setMessage(message: String?) {
        asJ.setMessage(message)
    }

    /**
     * @see AddNoteCommand.setNotesRef
     */
    fun setNotesRef(notesRef: String?) {
        asJ.setNotesRef(notesRef)
    }
}
