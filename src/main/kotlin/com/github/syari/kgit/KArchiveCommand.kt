@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.ArchiveCommand
import org.eclipse.jgit.lib.ObjectId
import java.io.OutputStream

/**
 * @see ArchiveCommand
 */
class KArchiveCommand(asJ: ArchiveCommand) : KGitCommand<ArchiveCommand, OutputStream?>(asJ) {
    /**
     * @see ArchiveCommand.setTree
     */
    fun setTree(tree: ObjectId) {
        asJ.setTree(tree)
    }

    /**
     * @see ArchiveCommand.setPrefix
     */
    fun setPrefix(prefix: String?) {
        asJ.setPrefix(prefix)
    }

    /**
     * @see ArchiveCommand.setFilename
     */
    fun setFilename(filename: String) {
        asJ.setFilename(filename)
    }

    /**
     * @see ArchiveCommand.setOutputStream
     */
    fun setOutputStream(out: OutputStream) {
        asJ.setOutputStream(out)
    }

    /**
     * @see ArchiveCommand.setFormat
     */
    fun setFormat(fmt: String?) {
        asJ.setFormat(fmt)
    }

    /**
     * @see ArchiveCommand.setFormatOptions
     */
    fun setFormatOptions(options: Map<String, Any>) {
        asJ.setFormatOptions(options)
    }

    /**
     * @see ArchiveCommand.setPaths
     */
    fun setPaths(vararg paths: String) {
        asJ.setPaths(*paths)
    }
}
