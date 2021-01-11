@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.DeleteTagCommand

/**
 * @see DeleteTagCommand
 */
class KDeleteTagCommand(asJ: DeleteTagCommand) : KGitCommand<DeleteTagCommand, List<String>>(asJ) {
    /**
     * @see DeleteTagCommand.setTags
     */
    fun setTags(vararg tags: String) {
        asJ.setTags(*tags)
    }
}
