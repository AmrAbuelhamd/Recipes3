package com.blogspot.soyamr.domain.auth

import com.blogspot.soyamr.domain.auth.model.AuthData
import com.blogspot.soyamr.domain.auth.model.Session
import com.blogspot.soyamr.domain.FlowUseCase
import com.blogspot.soyamr.domain.auth.AuthDbDataSource
import com.blogspot.soyamr.domain.auth.AuthRemoteDataSource
import com.blogspot.soyamr.domain.auth.model.SessionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LoginUseCase : FlowUseCase<LoginParams, AuthData>

class LoginUseCaseImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val dataSource: AuthDbDataSource,
) : LoginUseCase {
    override fun execute(param: LoginParams): Flow<AuthData> = flow {
        val authData: AuthData = authRemoteDataSource.loginByPhone(param.phone, "")
        Session(
            phone = param.phone,
            accessToken = authData.accessToken,
            refreshToken = authData.refreshToken,
            type = SessionType.AUTHORIZED_USER
        ).also { session ->
            dataSource.saveSession(session)
        }
        emit(authData)
    }
}

data class LoginParams(
    val phone: String,
)
