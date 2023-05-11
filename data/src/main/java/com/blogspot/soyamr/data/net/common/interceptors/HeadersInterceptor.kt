package com.blogspot.soyamr.data.net.common.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

@Suppress("TooGenericExceptionCaught", "SwallowedException")
class HeadersInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
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
        }.build()

        return try {
            response = chain.proceed(request)
            response
        } catch (e: Exception) {
            chain.proceed(request)
        }
    }

    companion object {
        const val GET_METHOD: String = "GET"
        const val POST_METHOD: String = "POST"
    }
}
