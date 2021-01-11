package com.github.syari.kgit

import org.eclipse.jgit.api.SubmoduleAddCommand
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Repository

/**
 * @see SubmoduleAddCommand
 */
class KSubmoduleAddCommand(asJ: SubmoduleAddCommand): KTransportCommand<SubmoduleAddCommand, Repository?>(asJ) {
    /**
     * @see SubmoduleAddCommand.setName
     */
    fun setName(name: String?) {
        asJ.setName(name)
    }

    /**
     * @see SubmoduleAddCommand.setPath
     */
    fun setPath(path: String?) {
        asJ.setPath(path)
    }
    
    /**
     * @see SubmoduleAddCommand.setURI
     */
    fun setURI(uri: String?) {
        asJ.setURI(uri)
    }

    /**
     * @see SubmoduleAddCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }
}