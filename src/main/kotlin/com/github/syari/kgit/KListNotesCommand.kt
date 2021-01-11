@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.ListNotesCommand
import org.eclipse.jgit.notes.Note

/**
 * @see ListNotesCommand
 */
class KListNotesCommand(asJ: ListNotesCommand): KGitCommand<ListNotesCommand, List<Note>>(asJ) {
    /**
     * @see ListNotesCommand.setNotesRef
     */
    fun setNotesRef(notesRef: String?) {
        asJ.setNotesRef(notesRef)
    }}