package com.github.syari.kgit

interface KWrapperCallable<T : KWrapper<S>, S> {
    fun callAsK(): T
}
