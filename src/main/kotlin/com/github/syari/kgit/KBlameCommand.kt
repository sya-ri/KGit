@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.BlameCommand
import org.eclipse.jgit.blame.BlameResult
import org.eclipse.jgit.diff.DiffAlgorithm
import org.eclipse.jgit.diff.RawTextComparator
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.ObjectId

/**
 * @see BlameCommand
 */
class KBlameCommand(asJ: BlameCommand) : KGitCommand<BlameCommand, BlameResult?>(asJ) {
    /**
     * @see BlameCommand.setFilePath
     */
    fun setFilePath(filePath: String?) {
        asJ.setFilePath(filePath)
    }

    /**
     * @see BlameCommand.setDiffAlgorithm
     */
    fun setDiffAlgorithm(diffAlgorithm: DiffAlgorithm?) {
        asJ.setDiffAlgorithm(diffAlgorithm)
    }

    /**
     * @see BlameCommand.setTextComparator
     */
    fun setTextComparator(textComparator: RawTextComparator?) {
        asJ.setTextComparator(textComparator)
    }

    /**
     * @see BlameCommand.setStartCommit
     */
    fun setStartCommit(commit: AnyObjectId?) {
        asJ.setStartCommit(commit)
    }

    /**
     * @see BlameCommand.setFollowFileRenames
     */
    fun setFollowFileRenames(follow: Boolean) {
        asJ.setFollowFileRenames(follow)
    }

    /**
     * @see BlameCommand.reverse
     */
    fun reverse(start: AnyObjectId, end: AnyObjectId) {
        asJ.reverse(start, end)
    }

    /**
     * @see BlameCommand.reverse
     */
    fun reverse(start: AnyObjectId, end: Collection<ObjectId>?) {
        asJ.reverse(start, end)
    }
}
