package com.blogspot.soyamr.domain.auth

import com.blogspot.soyamr.domain.auth.model.AuthData

interface AuthRemoteDataSource {
    suspend fun loginByPhone(email: String, password: String): AuthData
}