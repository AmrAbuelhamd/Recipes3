package com.blogspot.soyamr.domain.helpers

import com.blogspot.soyamr.domain.common.Constants
import com.blogspot.soyamr.domain.common.extentions.orZero
import com.blogspot.soyamr.domain.dataStore.DataStoreSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val EXPIRATION_TIME = Constants.ONE_MINUTE_IN_MS * 5

interface CacheExpirationHelper {
    suspend fun isCachedListExpired(): Boolean
    suspend fun isCachedItemExpired(lastUpdatedInCache: Long?): Boolean
    suspend fun updateListLastUpdateTimeStamp(ts: Long = System.currentTimeMillis())
}

class CacheExpirationHelperImpl @Inject constructor(
    private val dataStore: DataStoreSource
) : CacheExpirationHelper {
    override suspend fun isCachedListExpired(): Boolean {
        val current = System.currentTimeMillis()
        val lastUpdate = dataStore.listLastUpdateTimeStamp.first()
        return current - lastUpdate > EXPIRATION_TIME
    }

    override suspend fun isCachedItemExpired(lastUpdatedInCache: Long?): Boolean {
        val current = System.currentTimeMillis()
        return current - lastUpdatedInCache.orZero() > EXPIRATION_TIME
    }

    override suspend fun updateListLastUpdateTimeStamp(ts: Long) {
        dataStore.updateLastUpdateTimeStamp(ts)
    }
}