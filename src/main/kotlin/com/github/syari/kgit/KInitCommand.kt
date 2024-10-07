@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.InitCommand
import org.eclipse.jgit.util.FS
import java.io.File
import java.util.concurrent.Callable

/**
 * @see InitCommand
 */
class KInitCommand(private val asJ: InitCommand = InitCommand()) :
    Callable<Git>,
    KWrapperCallable<KGit, Git> {
    /**
     * @see InitCommand.call
     */
    override fun call(): Git = asJ.call()

    /**
     * @see CloneCommand.call
     */
    override fun callAsK() = KGit(call())

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

    /**
     * @see InitCommand.setInitialBranch
     */
    fun setInitialBranch(branch: String?) {
        asJ.setInitialBranch(branch)
    }
}
