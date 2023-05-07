package com.blogspot.soyamr.data.net.common.interceptors

import com.blogspot.soyamr.data.net.common.model.HeaderFlags
import com.blogspot.soyamr.domain.auth.GetCurrentSessionUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

@Suppress("TooGenericExceptionCaught", "SwallowedException")
class HeadersInterceptor(
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val session = runBlocking { getCurrentSessionUseCase(Unit) }
        val response: Response?
        val baseRequest = chain.request()
        val request: Request = baseRequest.newBuilder().apply {
            // add common headers over all requests
            addHeader("Accept", "*/*")

            // add headers depending on method type
            when (baseRequest.method) {
                GET_METHOD -> {
                    addHeader("Accept", "application/json")
                }
                POST_METHOD -> {
                    addHeader("Content-Type", "application/json")
                }
            }

            // add no auth header for some requests
            if (baseRequest.header(HeaderFlags.NoToken.key) == HeaderFlags.NoToken.value) {
                removeHeader(HeaderFlags.NoToken.key)
            } else if (session.accessToken.isNotEmpty()) {
                addHeader(HEADER_AUTH, "$HEADER_BEARER ${session.accessToken}")
            }
        }.build()

        return try {
            response = chain.proceed(request)
            response
        } catch (e: Exception) {
            chain.proceed(request)
        }
    }

    companion object {
        const val HEADER_AUTH: String = "Authorization"
        const val HEADER_BEARER: String = "Bearer"
        const val GET_METHOD: String = "GET"
        const val POST_METHOD: String = "POST"
    }
}
