package com.github.syari.kgit

import org.eclipse.jgit.api.StashListCommand
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @see StashListCommand
 */
class KStashListCommand(asJ: StashListCommand): KGitCommand<StashListCommand, Collection<RevCommit>>(asJ)