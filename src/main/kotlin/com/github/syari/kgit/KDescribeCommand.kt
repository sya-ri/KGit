@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.DescribeCommand
import org.eclipse.jgit.lib.ObjectId

/**
 * @see DescribeCommand
 */
class KDescribeCommand(asJ: DescribeCommand) : KGitCommand<DescribeCommand, String?>(asJ) {
    /**
     * @see DescribeCommand.setTarget
     */
    fun setTarget(target: ObjectId?) {
        asJ.setTarget(target)
    }

    /**
     * @see DescribeCommand.setTarget
     */
    fun setTarget(rev: String) {
        asJ.setTarget(rev)
    }

    /**
     * @see DescribeCommand.setLong
     */
    fun setLong(longDesc: Boolean) {
        asJ.setLong(longDesc)
    }

    /**
     * @see DescribeCommand.setAll
     */
    fun setAll(all: Boolean) {
        asJ.setAll(all)
    }

    /**
     * @see DescribeCommand.setTags
     */
    fun setTags(tags: Boolean) {
        asJ.setTags(tags)
    }

    /**
     * @see DescribeCommand.setAlways
     */
    fun setAlways(always: Boolean) {
        asJ.setAlways(always)
    }

    /**
     * @see DescribeCommand.setMatch
     */
    fun setMatch(vararg patterns: String) {
        asJ.setMatch(*patterns)
    }
}
