package com.github.syari.kgit.api

import com.github.syari.kgit.KGit
import org.eclipse.jgit.api.InitCommand
import org.eclipse.jgit.util.FS
import java.io.File

/**
 * @see InitCommand
 */
class KInitCommand(private val asJ: InitCommand = InitCommand()) {
    /**
     * @see InitCommand.call
     */
    fun call() = KGit(asJ.call())

    /**
     * @see InitCommand.setDirectory
     */
    var directory: File? = null
        set(value) {
            field = value.apply(asJ::setDirectory)
        }

    /**
     * @see InitCommand.setGitDir
     */
    var gitDir: File? = null
        set(value) {
            field = value.apply(asJ::setGitDir)
        }

    /**
     * @see InitCommand.setBare
     */
    var bare: Boolean = false
        set(value) {
            field = value.apply(asJ::setBare)
        }

    /**
     * @see InitCommand.setFs
     */
    var fs: FS? = null
        set(value) {
            field = value.apply(asJ::setFs)
        }
}