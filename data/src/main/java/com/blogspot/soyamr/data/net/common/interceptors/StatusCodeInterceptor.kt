package com.blogspot.soyamr.data.net.common.interceptors

import com.blogspot.soyamr.data.net.common.parseErrorResponse
import com.blogspot.soyamr.data.net.common.tryProceed
import com.blogspot.soyamr.domain.auth.LogoutUseCase
import com.blogspot.soyamr.domain.common.model.CommonBackendFailure
import com.blogspot.soyamr.domain.common.model.UnauthorizedFailure
import com.blogspot.soyamr.domain.common.model.UnknownFailure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

@Suppress("MagicNumber", "TooGenericExceptionCaught", "SwallowedException")
class StatusCodeInterceptor(
    private val appScope: CoroutineScope,
    private val logoutUseCase: LogoutUseCase,
    private val deserializer: Json
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return handleResponse(chain.tryProceed(request))
    }

    private fun handleResponse(response: Response): Response {
        val exceptionToThrow = when (response.code) {
            in HttpURLConnection.HTTP_OK..HttpURLConnection.HTTP_MULT_CHOICE -> null
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                logout()
                UnauthorizedFailure(response.parseErrorResponse(deserializer))
            }
            else -> {
                val apiError = response.parseErrorResponse(deserializer)
                if (apiError != null) {
                    CommonBackendFailure(apiError)
                } else {
                    UnknownFailure(response.message)
                }
            }
        }
        exceptionToThrow?.let { throw it }

        return response
    }

    private fun logout() {
        appScope.launch {
            logoutUseCase(Unit)
        }
    }
}
