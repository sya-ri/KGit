package com.github.syari.kgit

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
    fun setDirectory(directory: File?) {
        asJ.setDirectory(directory)
    }

    /**
     * @see InitCommand.setGitDir
     */
    fun setGitDir(gitDir: File?) {
        asJ.setGitDir(gitDir)
    }

    /**
     * @see InitCommand.setBare
     */
    fun setBare(bare: Boolean) {
        asJ.setBare(bare)
    }

    /**
     * @see InitCommand.setFs
     */
    fun setFs(fs: FS?) {
        asJ.setFs(fs)
    }
}