package com.blogspot.soyamr.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.blogspot.soyamr.domain.common.extentions.orZero
import com.blogspot.soyamr.domain.dataStore.DataStoreSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(DataStoreSourceImpl.STORAGE_NAME)

class DataStoreSourceImpl(context: Context) : DataStoreSource {
    private val dataStore = context.dataStore

    override val listLastUpdateTimeStamp: Flow<Long> = dataStore.data
        .map { preferences ->
            preferences[LAST_UPDATE_TIME_STAMP].orZero()
        }

    override suspend fun updateLastUpdateTimeStamp(ts: Long) {
        dataStore.edit { preferences ->
            preferences[LAST_UPDATE_TIME_STAMP] = ts
        }
    }

    companion object {
        const val STORAGE_NAME: String = "RECIPES"
        private val LAST_UPDATE_TIME_STAMP = longPreferencesKey("last_update_time_stamp")
    }
}