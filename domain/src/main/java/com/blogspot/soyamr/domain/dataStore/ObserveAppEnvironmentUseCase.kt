package com.blogspot.soyamr.domain.dataStore

import com.blogspot.soyamr.domain.LocalFlowUseCase
import com.blogspot.soyamr.domain.dataStore.model.AppEnvironment
import javax.inject.Inject

interface ObserveAppEnvironmentUseCase : LocalFlowUseCase<Unit, AppEnvironment>

class ObserveAppEnvironmentUseCaseImpl @Inject constructor(
    private val dataStore: DataStoreSource
) : ObserveAppEnvironmentUseCase {
    override fun execute(param: Unit) = dataStore.appEnvironment
}