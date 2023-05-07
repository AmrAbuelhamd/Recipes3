package com.blogspot.soyamr.data.net.auth

import com.blogspot.soyamr.data.net.auth.model.*
import com.blogspot.soyamr.data.net.common.model.HeaderFlags
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers(HeaderFlags.NoToken.formattedHeader)
    @POST("authByEmail")
    suspend fun authByEmail(@Body body: AuthRequestBody): AuthDataResponse
}