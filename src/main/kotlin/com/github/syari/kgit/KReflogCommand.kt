package com.github.syari.kgit

import org.eclipse.jgit.api.ReflogCommand
import org.eclipse.jgit.lib.ReflogEntry

/**
 * @see ReflogCommand
 */
class KReflogCommand(asJ: ReflogCommand): KGitCommand<ReflogCommand, Collection<ReflogEntry>>(asJ) {
    /**
     * @see ReflogCommand.setRef
     */
    fun setRef(ref: String) {
        asJ.setRef(ref)
    }
}