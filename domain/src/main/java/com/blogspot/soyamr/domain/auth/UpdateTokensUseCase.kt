package com.blogspot.soyamr.domain.auth

import com.blogspot.soyamr.domain.SuspendedUseCase
import javax.inject.Inject

interface UpdateTokensUseCase : SuspendedUseCase<UpdateTokensParams, Unit>

class UpdateTokensUseCaseImpl @Inject constructor(
    private val authDbDataSource: AuthDbDataSource,
) : UpdateTokensUseCase {
    override suspend fun execute(param: UpdateTokensParams) {
        val session = authDbDataSource.getSession()
        session.copy(
            accessToken = param.accessToken,
            refreshToken = param.refreshToken
        ).also {
            authDbDataSource.saveSession(it)
        }
    }
}

data class UpdateTokensParams(
    val accessToken: String,
    val refreshToken: String,
)
