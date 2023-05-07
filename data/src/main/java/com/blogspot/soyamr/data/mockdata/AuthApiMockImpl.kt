package com.blogspot.soyamr.data.mockdata

import com.blogspot.soyamr.data.net.auth.AuthApi
import com.blogspot.soyamr.data.net.auth.model.AuthDataResponse
import com.blogspot.soyamr.data.net.auth.model.AuthRequestBody
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class AuthApiMockImpl @Inject constructor() : AuthApi {
    override suspend fun authByEmail(body: AuthRequestBody): AuthDataResponse {
        delay(500)
        return when (Random.nextBoolean()) {
            true -> AuthDataResponse()
            false -> throw Exception("Something went wrong")
        }
    }
}