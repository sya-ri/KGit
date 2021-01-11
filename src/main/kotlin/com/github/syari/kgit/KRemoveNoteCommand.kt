@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.RemoveNoteCommand
import org.eclipse.jgit.notes.Note
import org.eclipse.jgit.revwalk.RevObject

/**
 * @see RemoveNoteCommand
 */
class KRemoveNoteCommand(asJ: RemoveNoteCommand): KGitCommand<RemoveNoteCommand, Note?>(asJ) {
    /**
     * @see RemoveNoteCommand.setObjectId
     */
    fun setObjectId(id: RevObject?) {
        asJ.setObjectId(id)
    }

    /**
     * @see RemoveNoteCommand.setNotesRef
     */
    fun setNotesRef(notesRef: String?) {
        asJ.setNotesRef(notesRef)
    }
}