@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.NameRevCommand
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.Ref

/**
 * @see NameRevCommand
 */
class KNameRevCommand(asJ: NameRevCommand) : KGitCommand<NameRevCommand, Map<ObjectId, String>>(asJ) {
    /**
     * @see NameRevCommand.add
     */
    fun add(id: ObjectId) {
        asJ.add(id)
    }

    /**
     * @see NameRevCommand.add
     */
    fun add(ids: Iterable<ObjectId>) {
        asJ.add(ids)
    }

    /**
     * @see NameRevCommand.addPrefix
     */
    fun addPrefix(prefix: String?) {
        asJ.addPrefix(prefix)
    }

    /**
     * @see NameRevCommand.addAnnotatedTags
     */
    fun addAnnotatedTags() {
        asJ.addAnnotatedTags()
    }

    /**
     * @see NameRevCommand.addRef
     */
    fun addRef(ref: Ref?) {
        asJ.addRef(ref)
    }
}
