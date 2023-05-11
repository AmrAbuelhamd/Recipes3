package com.blogspot.soyamr.domain.dataStore

import kotlinx.coroutines.flow.Flow

interface DataStoreSource {

    val listLastUpdateTimeStamp: Flow<Long>
    suspend fun updateLastUpdateTimeStamp(ts: Long)
}