package com.blogspot.soyamr.domain.auth

import com.blogspot.soyamr.domain.SuspendedUseCase
import com.blogspot.soyamr.domain.dataStore.DataStoreSource
import javax.inject.Inject

interface LogoutUseCase : SuspendedUseCase<Unit, Unit>

@Suppress("LongParameterList")
class LogoutUseCaseImpl @Inject constructor(
    private val dataStore: DataStoreSource,
    private val authDbDataSource: AuthDbDataSource
) : LogoutUseCase {

    override suspend fun execute(param: Unit) {
        dataStore.clear()
        authDbDataSource.clearSessions()
    }
}
