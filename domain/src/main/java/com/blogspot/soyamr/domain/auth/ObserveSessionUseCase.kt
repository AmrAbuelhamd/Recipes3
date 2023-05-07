package com.blogspot.soyamr.domain.auth

import com.blogspot.soyamr.domain.auth.model.Session
import com.blogspot.soyamr.domain.LocalFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveSessionUseCase : LocalFlowUseCase<Unit, Session>

class ObserveSessionUseCaseImpl @Inject constructor(
    private val dataSource: AuthDbDataSource,
) : ObserveSessionUseCase {

    override fun execute(param: Unit): Flow<Session> = dataSource.observeSession()
}
