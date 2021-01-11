@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.RemoteListCommand
import org.eclipse.jgit.transport.RemoteConfig

/**
 * @see RemoteListCommand
 */
class KRemoteListCommand(asJ: RemoteListCommand) : KGitCommand<RemoteListCommand, List<RemoteConfig>>(asJ)
