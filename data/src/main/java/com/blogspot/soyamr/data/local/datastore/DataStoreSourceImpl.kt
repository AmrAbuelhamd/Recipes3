package com.blogspot.soyamr.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.blogspot.soyamr.domain.dataStore.DataStoreSource
import com.blogspot.soyamr.domain.dataStore.model.AppEnvironment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(DataStoreSourceImpl.STORAGE_NAME)

class DataStoreSourceImpl(context: Context) : DataStoreSource {
    private val dataStore = context.dataStore

    override val appEnvironment: Flow<AppEnvironment> = dataStore.data
        .map { preferences ->
            val serverUrl = preferences[SERVER_URL] ?: ""
            AppEnvironment.values().firstOrNull { it.url == serverUrl } ?: AppEnvironment.DEV
        }

    override suspend fun updateAppEnvironment(environment: AppEnvironment) {
        dataStore.edit { preferences ->
            preferences[SERVER_URL] = environment.url
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(SERVER_URL)
        }
    }

    companion object {
        const val STORAGE_NAME: String = "BASE_CODE"
        private val SERVER_URL = stringPreferencesKey("SERVER_URL")
    }
}