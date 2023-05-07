package com.blogspot.soyamr.data.net.auth

import com.blogspot.soyamr.data.net.auth.model.AuthRequestBody
import com.blogspot.soyamr.data.net.common.Mock
import com.blogspot.soyamr.data.net.common.NetConverters.toDomain
import com.blogspot.soyamr.domain.auth.AuthRemoteDataSource
import com.blogspot.soyamr.domain.auth.model.AuthData
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    @Mock private val authApi: AuthApi
) : AuthRemoteDataSource {
    override suspend fun loginByPhone(email: String, password: String): AuthData {
        return authApi.authByEmail(AuthRequestBody(phone = email)).toDomain()
    }
}