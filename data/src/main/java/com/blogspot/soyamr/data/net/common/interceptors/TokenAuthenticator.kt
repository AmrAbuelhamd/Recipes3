package com.blogspot.soyamr.data.net.common.interceptors

import com.blogspot.soyamr.data.net.auth.model.AuthDataResponse
import com.blogspot.soyamr.data.net.auth.model.RefreshTokenRequest
import com.blogspot.soyamr.data.net.common.responseCount
import com.blogspot.soyamr.data.net.common.interceptors.HeadersInterceptor.Companion.HEADER_AUTH
import com.blogspot.soyamr.domain.auth.ClearSessionUseCase
import com.blogspot.soyamr.domain.auth.GetCurrentSessionUseCase
import com.blogspot.soyamr.domain.auth.UpdateTokensParams
import com.blogspot.soyamr.domain.auth.UpdateTokensUseCase
import com.blogspot.soyamr.domain.auth.model.SessionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Suppress("LongParameterList", "MagicNumber", "ReturnCount")
class TokenAuthenticator(
    private val appScope: CoroutineScope,
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val updateTokensUseCase: UpdateTokensUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
    private val apiUrl: String,
    private val serializer: Json,
    private val isDebugEnvironment: Boolean,
) : Authenticator {

    private val maxFailCount = 3

    override fun authenticate(route: Route?, response: Response): Request? {
        val session = runBlocking { getCurrentSessionUseCase(Unit) }
        if (session.type != SessionType.AUTHORIZED_USER) {
            return null
        }

        if (session.refreshToken.isBlank()) {
            return null
        }

        if (response.responseCount >= maxFailCount) {
            return null // If we've failed maxFailCount, give up.
        }

        val tokenResponse = refreshToken(RefreshTokenRequest(session.refreshToken))
        if (tokenResponse != null) {
            // TODO: improve this
            appScope.launch {
                updateTokensUseCase(
                    UpdateTokensParams(
                        accessToken = tokenResponse.token.orEmpty(),
                        refreshToken = session.refreshToken
                    )
                )
            }
        } else {
            return null
        }

        return response.request.newBuilder()
            .header(HEADER_AUTH, tokenResponse.token.orEmpty()).build()
    }

    private fun refreshToken(refreshToken: RefreshTokenRequest): AuthDataResponse? {
        val okHttpClient = createOkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val request = Request.Builder()
            .url("$apiUrl$REFRESH_PATH")
            .post(
                serializer.encodeToString(RefreshTokenRequest.serializer(), refreshToken)
                    .toRequestBody(mediaType)
            )
            .build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful) {
            try {
                val body = response.body?.string()
                if (body.isNullOrBlank()) null
                else serializer.decodeFromString(AuthDataResponse.serializer(), body)
            } catch (e: SerializationException) {
                null
            }
        } else {
            appScope.launch {
                clearSessionUseCase(Unit)
            }
            null
        }
    }

    private fun createOkHttpClient(): OkHttpClient { // build new client here to prevent dependency cycle
        val httpClientBuilder = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            if (isDebugEnvironment) {
                val logLevel = HttpLoggingInterceptor.Level.BODY
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                addInterceptor(
                    httpLoggingInterceptor.apply {
                        httpLoggingInterceptor.level = logLevel
                    }
                )
            }
        }
        return httpClientBuilder.build()
    }

    private companion object {
        const val REFRESH_PATH = "/tokenRefresh"
    }
}
