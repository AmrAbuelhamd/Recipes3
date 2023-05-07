package com.blogspot.soyamr.recipes3.app.hilt.network

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class LoggingInterceptor

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class HeadersInterceptor

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class TokenAuthenticator

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class StatusCodeInterceptor

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class BaseUrl

