package com.github.syari.kgit

import org.eclipse.jgit.api.ShowNoteCommand
import org.eclipse.jgit.notes.Note
import org.eclipse.jgit.revwalk.RevObject

class KShowNoteCommand(asJ: ShowNoteCommand): KGitCommand<ShowNoteCommand, Note>(asJ) {
    /**
     * @see ShowNoteCommand.setObjectId
     */
    fun setObjectId(id: RevObject?) {
        asJ.setObjectId(id)
    }

    /**
     * @see ShowNoteCommand.setNotesRef
     */
    fun setNotesRef(notesRef: String?) {
        asJ.setNotesRef(notesRef)
    }}