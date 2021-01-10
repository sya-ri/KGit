package com.github.syari.kgit

import org.eclipse.jgit.api.ArchiveCommand
import org.eclipse.jgit.lib.ObjectId
import java.io.OutputStream

/**
 * @see ArchiveCommand
 */
class KArchiveCommand(private val asJ: ArchiveCommand) {
    /**
     * @see ArchiveCommand.call
     */
    fun call(): OutputStream? = asJ.call()

    /**
     * @see ArchiveCommand.setTree
     */
    var tree: ObjectId? = null
        set(value) {
            value?.let { field = it.apply(asJ::setTree) }
        }

    /**
     * @see ArchiveCommand.setPrefix
     */
    var prefix: String? = null
        set(value) {
            field = value.apply(asJ::setPrefix)
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
     * @see ArchiveCommand.setFormatOptions
     */
    fun setFormatOptions(options: Map<String, Any>) {
        asJ.setFormatOptions(options)
    }

    /**
     * @see ArchiveCommand.setPaths
     */
    fun setPaths(vararg paths: String?) {
        asJ.setPaths(*paths)
    }
}