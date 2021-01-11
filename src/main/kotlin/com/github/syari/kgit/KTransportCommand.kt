package com.github.syari.kgit

import org.eclipse.jgit.api.TransportCommand
import org.eclipse.jgit.api.TransportConfigCallback
import org.eclipse.jgit.transport.CredentialsProvider

abstract class KTransportCommand<T : TransportCommand<*, S>, S>(asJ: T) : KGitCommand<T, S>(asJ) {
    fun setCredentialsProvider(credentialsProvider: CredentialsProvider?) {
        asJ.setCredentialsProvider(credentialsProvider)
    }

    fun setTimeout(timeout: Int) {
        asJ.setTimeout(timeout)
    }

    fun setTransportConfigCallback(transportConfigCallback: TransportConfigCallback) {
        asJ.setTransportConfigCallback(transportConfigCallback)
    }
}
