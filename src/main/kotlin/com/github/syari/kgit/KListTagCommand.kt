@file:Suppress("unused")

package com.github.syari.kgit

import org.eclipse.jgit.api.ListTagCommand
import org.eclipse.jgit.lib.Ref

/**
 * @see ListTagCommand
 */
class KListTagCommand(asJ: ListTagCommand): KGitCommand<ListTagCommand, List<Ref>>(asJ)