@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.ApplyCommand
import org.eclipse.jgit.api.ApplyResult
import java.io.InputStream

/**
 * @see ApplyCommand
 */
class KApplyCommand(asJ: ApplyCommand) : KGitCommand<ApplyCommand, ApplyResult>(asJ) {
    /**
     * @see ApplyCommand.setPatch
     */
    fun setPatch(`in`: InputStream?) {
        asJ.setPatch(`in`)
    }
}
