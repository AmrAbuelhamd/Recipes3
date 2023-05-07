package com.blogspot.soyamr.domain.dataStore

import com.blogspot.soyamr.domain.SuspendedUseCase
import com.blogspot.soyamr.domain.dataStore.model.AppEnvironment
import javax.inject.Inject

interface UpdateAppEnvironmentUseCase : SuspendedUseCase<AppEnvironment, Unit>

class UpdateAppEnvironmentUseCaseImpl @Inject constructor(
    private val dataStore: DataStoreSource
) : UpdateAppEnvironmentUseCase {
    override suspend fun execute(param: AppEnvironment) =
        dataStore.updateAppEnvironment(param)
}