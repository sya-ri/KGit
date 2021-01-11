@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.PushCommand
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.transport.PushResult
import org.eclipse.jgit.transport.RefLeaseSpec
import org.eclipse.jgit.transport.RefSpec
import java.io.OutputStream

/**
 * @see PushCommand
 */
class KPushCommand(asJ: PushCommand) : KTransportCommand<PushCommand, Iterable<PushResult>>(asJ) {
    /**
     * @see PushCommand.setRemote
     * @see PushCommand.getRemote
     */
    var remote: String by asJ::remote

    /**
     * @see PushCommand.setReceivePack
     * @see PushCommand.getReceivePack
     */
    var receivePack: String by asJ::receivePack

    /**
     * @see PushCommand.getTimeout
     */
    val timeout: Int by asJ::timeout

    /**
     * @see PushCommand.setProgressMonitor
     * @see PushCommand.getProgressMonitor
     */
    var progressMonitor: ProgressMonitor by asJ::progressMonitor

    /**
     * @see PushCommand.setRefLeaseSpecs
     * @see PushCommand.getRefLeaseSpecs
     */
    var refLeaseSpecs: List<RefLeaseSpec> by asJ::refLeaseSpecs

    /**
     * @see PushCommand.setRefLeaseSpecs
     */
    fun setRefLeaseSpecs(vararg specs: RefLeaseSpec) {
        asJ.setRefLeaseSpecs(*specs)
    }

    /**
     * @see PushCommand.setRefSpecs
     * @see PushCommand.getRefSpecs
     */
    var refSpecs: List<RefSpec> by asJ::refSpecs

    /**
     * @see PushCommand.setRefSpecs
     */
    fun setRefSpecs(vararg specs: RefSpec) {
        asJ.setRefSpecs(*specs)
    }

    /**
     * @see PushCommand.setPushAll
     */
    fun setPushAll() {
        asJ.setPushAll()
    }

    /**
     * @see PushCommand.setPushTags
     */
    fun setPushTags() {
        asJ.setPushTags()
    }

    /**
     * @see PushCommand.add
     */
    fun add(ref: Ref) {
        asJ.add(ref)
    }

    /**
     * @see PushCommand.add
     */
    fun add(nameOrSpec: String) {
        asJ.add(nameOrSpec)
    }

    /**
     * @see PushCommand.setDryRun
     * @see PushCommand.isDryRun
     */
    var isDryRun: Boolean
        set(value) {
            asJ.isDryRun = value
        }
        get() = asJ.isDryRun

    /**
     * @see PushCommand.setThin
     * @see PushCommand.isThin
     */
    var isThin: Boolean
        set(value) {
            asJ.isThin = value
        }
        get() = asJ.isThin

    /**
     * @see PushCommand.setAtomic
     * @see PushCommand.isAtomic
     */
    var isAtomic: Boolean
        set(value) {
            asJ.isAtomic = value
        }
        get() = asJ.isAtomic

    /**
     * @see PushCommand.setForce
     * @see PushCommand.isForce
     */
    var isForce: Boolean
        set(value) {
            asJ.isForce = value
        }
        get() = asJ.isForce

    /**
     * @see PushCommand.setOutputStream
     */
    fun setOutputStream(out: OutputStream?) {
        asJ.setOutputStream(out)
    }

    /**
     * @see PushCommand.setPushOptions
     * @see PushCommand.getPushOptions
     */
    var pushOptions: List<String> by asJ::pushOptions
}
