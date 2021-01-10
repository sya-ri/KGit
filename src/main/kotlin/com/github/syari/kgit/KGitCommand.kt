package com.github.syari.kgit

import org.eclipse.jgit.api.GitCommand
import org.eclipse.jgit.lib.Repository
import java.util.concurrent.Callable

abstract class KGitCommand<T: GitCommand<S>, S>(asJ: T): Callable<S>, KWrapper<T>(asJ) {
    val repository: Repository by asJ::repository

    override fun call(): S = asJ.call()
}