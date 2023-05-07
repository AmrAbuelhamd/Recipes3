package com.blogspot.soyamr.data.net.common

import com.blogspot.soyamr.data.net.common.NetConverters.toDomain
import com.blogspot.soyamr.data.net.common.model.ApiErrorResponse
import com.blogspot.soyamr.domain.common.model.ApiError
import com.blogspot.soyamr.domain.common.model.NoInternetFailure
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

@Suppress("TooGenericExceptionCaught", "UseIfInsteadOfWhen")
fun Interceptor.Chain.tryProceed(request: Request): Response = try {
    this.proceed(request)
} catch (e: Exception) {
    throw when (e) {
        is IOException -> NoInternetFailure(message = e.message)
        else -> e
    }
}

val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()

fun Response.parseErrorResponse(json: Json): ApiError? {
    val responseBody = body?.string()
    return if (responseBody.isNullOrBlank()) {
        null
    } else {
        try {
            json.decodeFromString(ApiErrorResponse.serializer(), responseBody).toDomain()
        } catch (e: Exception) {
            null
        }
    }
}