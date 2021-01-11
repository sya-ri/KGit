package com.github.syari.kgit

import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.FetchCommand
import org.eclipse.jgit.api.SubmoduleUpdateCommand
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.merge.MergeStrategy

/**
 * @see SubmoduleUpdateCommand
 */
class KSubmoduleUpdateCommand(asJ: SubmoduleUpdateCommand): KTransportCommand<SubmoduleUpdateCommand, Collection<String>>(asJ) {
    /**
     * @see SubmoduleUpdateCommand.setProgressMonitor
     */
    fun setProgressMonitor(monitor: ProgressMonitor?) {
        asJ.setProgressMonitor(monitor)
    }

    /**
     * @see SubmoduleUpdateCommand.setFetch
     */
    fun setFetch(fetch: Boolean) {
        asJ.setFetch(fetch)
    }

    /**
     * @see SubmoduleUpdateCommand.addPath
     */
    fun addPath(path: String) {
        asJ.addPath(path)
    }

    /**
     * @see SubmoduleUpdateCommand.setStrategy
     */
    fun setStrategy(strategy: MergeStrategy) {
        asJ.setStrategy(strategy)
    }

    /**
     * @see SubmoduleUpdateCommand.setCallback
     */
    fun setCallback(callback: CloneCommand.Callback?) {
        asJ.setCallback(callback)
    }

    /**
     * @see SubmoduleUpdateCommand.setFetchCallback
     */
    fun setFetchCallback(callback: FetchCommand.Callback?) {
        asJ.setFetchCallback(callback)
    }
}