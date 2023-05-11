package com.blogspot.soyamr.recipes3.app.hilt.network

import com.blogspot.soyamr.data.net.common.Network
import com.blogspot.soyamr.recipes3.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun isDebugEnvironment(): Boolean = BuildConfig.DEBUG

    @Singleton
    @Provides
    fun provideJson() = Network.appJson

    @Singleton
    @Provides
    @BaseUrl
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideJsonFactory(json: Json): Converter.Factory = Network.getJsonFactory(json)

    @Provides
    @Singleton
    fun provideOkhttpCache() = Network.okHttpCache

    @Provides
    @Singleton
    @LoggingInterceptor
    fun provideLoggingInterceptor(
        isDebugEnvironment: Boolean
    ): Interceptor? = Network.getLoggingInterceptor(isDebugEnvironment)

    @Provides
    @Singleton
    @HeadersInterceptor
    fun provideHeadersInterceptor(): Interceptor = Network.getHeadersInterceptor()

    @Provides
    @Singleton
    @StatusCodeInterceptor
    fun provideStatusCodeInterceptor(
        deserializer: Json,
    ): Interceptor = Network.getStatusCodeInterceptor(
        deserializer = deserializer,
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        @LoggingInterceptor loggingInterceptor: Interceptor?,
        @HeadersInterceptor headersInterceptor: Interceptor,
        @StatusCodeInterceptor statusCodeInterceptor: Interceptor,
    ): OkHttpClient = Network.getHttpClient(
        interceptors = listOfNotNull(loggingInterceptor, headersInterceptor, statusCodeInterceptor),
        cache = cache,
    )

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        converter: Converter.Factory,
        @BaseUrl baseUrl: String,
    ): Retrofit = Network.getRetrofit(
        client = client,
        baseUrl = baseUrl,
        converterFactory = converter,
    )
}