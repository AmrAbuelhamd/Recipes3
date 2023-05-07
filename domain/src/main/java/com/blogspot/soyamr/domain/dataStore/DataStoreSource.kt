package com.blogspot.soyamr.domain.dataStore

import com.blogspot.soyamr.domain.dataStore.model.AppEnvironment
import kotlinx.coroutines.flow.Flow

interface DataStoreSource {

    val appEnvironment: Flow<AppEnvironment>
    suspend fun updateAppEnvironment(environment: AppEnvironment)

    suspend fun clear()
}