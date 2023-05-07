package com.blogspot.soyamr.domain.auth

import com.blogspot.soyamr.domain.auth.model.Session
import com.blogspot.soyamr.domain.SuspendedUseCase
import javax.inject.Inject

interface GetCurrentSessionUseCase : SuspendedUseCase<Unit, Session>

class GetCurrentSessionUseCaseImpl @Inject constructor(
    private val dataSource: AuthDbDataSource
) : GetCurrentSessionUseCase {

    override suspend fun execute(param: Unit): Session {
        return dataSource.getSession()
    }
}
