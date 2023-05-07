package com.blogspot.soyamr.recipes3.app.hilt.coroutine

import javax.inject.Qualifier

//dispatcher
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher

//scope
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope