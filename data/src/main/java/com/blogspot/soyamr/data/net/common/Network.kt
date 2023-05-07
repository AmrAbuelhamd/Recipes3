package com.blogspot.soyamr.data.net.common

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.blogspot.soyamr.data.net.common.interceptors.HeadersInterceptor
import com.blogspot.soyamr.data.net.common.interceptors.StatusCodeInterceptor
import com.blogspot.soyamr.data.net.common.interceptors.TokenAuthenticator
import com.blogspot.soyamr.domain.auth.ClearSessionUseCase
import com.blogspot.soyamr.domain.auth.GetCurrentSessionUseCase
import com.blogspot.soyamr.domain.auth.LogoutUseCase
import com.blogspot.soyamr.domain.auth.UpdateTokensUseCase
import com.blogspot.soyamr.domain.dataStore.BlockingGetBaseUrlUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

@Suppress("MagicNumber", "LongParameterList")
public object Network {
    private const val CONTENT_TYPE = "application/json"

    public val okHttpCache: Cache
        get() {
            val cacheDirectory = File("http-cache.tmp")
            val cacheSize = 50 * 1024 * 1024
            return Cache(cacheDirectory, cacheSize.toLong())
        }

    public val appJson: Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getJsonFactory(json: Json): Converter.Factory =
        json.asConverterFactory(contentType = CONTENT_TYPE.toMediaType())

    public fun getLoggingInterceptor(
        isDebugEnvironment: Boolean
    ): Interceptor? = if (isDebugEnvironment) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else null

    public fun getHeadersInterceptor(
        getCurrentSessionUseCase: GetCurrentSessionUseCase
    ): Interceptor = HeadersInterceptor(
        getCurrentSessionUseCase = getCurrentSessionUseCase
    )

    public fun getStatusCodeInterceptor(
        appScope: CoroutineScope,
        logoutUseCase: LogoutUseCase,
        deserializer: Json
    ): Interceptor = StatusCodeInterceptor(
        appScope = appScope,
        logoutUseCase = logoutUseCase,
        deserializer = deserializer
    )

    public fun getTokenAuthenticator(
        appScope: CoroutineScope,
        getCurrentSessionUseCase: GetCurrentSessionUseCase,
        updateTokensUseCase: UpdateTokensUseCase,
        clearUserDataUseCase: ClearSessionUseCase,
        baseUrl: String,
        serializer: Json,
        isDebugEnvironment: Boolean,
    ): Authenticator = TokenAuthenticator(
        appScope = appScope,
        getCurrentSessionUseCase = getCurrentSessionUseCase,
        updateTokensUseCase = updateTokensUseCase,
        apiUrl = baseUrl,
        serializer = serializer,
        isDebugEnvironment = isDebugEnvironment,
        clearSessionUseCase = clearUserDataUseCase,
    )

    public fun getHttpClient(
        cache: Cache,
        interceptors: List<Interceptor>?,
        authenticator: Authenticator?,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        cache(cache)
        interceptors?.forEach(::addInterceptor)
        authenticator?.also { authenticator(it) }
    }.build()

    public fun getRetrofit(
        client: OkHttpClient,
        blockingGetBaseUrlUseCase: BlockingGetBaseUrlUseCase,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(blockingGetBaseUrlUseCase(Unit))
        .addConverterFactory(converterFactory)
        .build()

    public inline fun <reified T> getApi(retrofit: Retrofit): T = retrofit.create(T::class.java)
}
