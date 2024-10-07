package com.github.syari.kgit

import org.eclipse.jgit.api.VerificationResult
import org.eclipse.jgit.api.VerifySignatureCommand
import org.eclipse.jgit.lib.GpgConfig

class KVerifySignatureCommand(asJ: VerifySignatureCommand) : KGitCommand<VerifySignatureCommand, Map<String, VerificationResult>>(asJ) {
    /**
     * @see VerifySignatureCommand.addName
     */
    fun addName(name: String?) {
        asJ.addName(name)
    }

    /**
     * @see VerifySignatureCommand.addNames
     */
    fun addNames(vararg names: String?) {
        asJ.addNames(*names)
    }

    /**
     * @see VerifySignatureCommand.addNames
     */
    fun addNames(names: Collection<String?>) {
        asJ.addNames(names)
    }

    /**
     * @see VerifySignatureCommand.setMode
     */
    fun setMode(mode: VerifySignatureCommand.VerifyMode) {
        asJ.setMode(mode)
    }

    /**
     * @see VerifySignatureCommand.setGpgConfig
     */
    fun setGpgConfig(config: GpgConfig?) {
        asJ.setGpgConfig(config)
    }
}
