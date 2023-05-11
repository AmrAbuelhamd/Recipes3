package com.blogspot.soyamr.data.net.common

import com.blogspot.soyamr.data.net.common.interceptors.HeadersInterceptor
import com.blogspot.soyamr.data.net.common.interceptors.StatusCodeInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
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

    public fun getHeadersInterceptor(): Interceptor = HeadersInterceptor()

    public fun getStatusCodeInterceptor(
        deserializer: Json
    ): Interceptor = StatusCodeInterceptor(
        deserializer = deserializer
    )

    public fun getHttpClient(
        cache: Cache,
        interceptors: List<Interceptor>?,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        cache(cache)
        interceptors?.forEach(::addInterceptor)
    }.build()

    public fun getRetrofit(
        client: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .build()

    public inline fun <reified T> getApi(retrofit: Retrofit): T = retrofit.create(T::class.java)
}
