package com.blogspot.soyamr.domain.auth

import com.blogspot.soyamr.domain.SuspendedUseCase
import javax.inject.Inject

interface ClearSessionUseCase : SuspendedUseCase<Unit, Unit>

class ClearSessionUseCaseImpl @Inject constructor(
    private val authDbDataSource: AuthDbDataSource,
) : ClearSessionUseCase {

    override suspend fun execute(param: Unit) {
        authDbDataSource.clearSessions()
    }
}
