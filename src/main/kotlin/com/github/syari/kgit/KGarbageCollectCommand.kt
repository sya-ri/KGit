@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.GarbageCollectCommand
import org.eclipse.jgit.lib.ProgressMonitor
import java.util.Date
import java.util.Properties

/**
 * @see GarbageCollectCommand
 */
class KGarbageCollectCommand(asJ: GarbageCollectCommand) : KGitCommand<GarbageCollectCommand, Properties>(asJ) {
    /**
     * @see GarbageCollectCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }

    /**
     * @see GarbageCollectCommand.setExpire
     */
    fun setExpire(expire: Date?) {
        asJ.setExpire(expire)
    }

    /**
     * @see GarbageCollectCommand.setAggressive
     */
    fun setAggressive(aggressive: Boolean) {
        asJ.setAggressive(aggressive)
    }

    /**
     * @see GarbageCollectCommand.setPreserveOldPacks
     */
    fun setPreserveOldPacks(preserveOldPacks: Boolean) {
        asJ.setPreserveOldPacks(preserveOldPacks)
    }

    /**
     * @see GarbageCollectCommand.setPrunePreserved
     */
    fun setPrunePreserved(prunePreserved: Boolean) {
        asJ.setPrunePreserved(prunePreserved)
    }

    /**
     * @see GarbageCollectCommand.getStatistics
     */
    val statistics: Properties by asJ::statistics
}
