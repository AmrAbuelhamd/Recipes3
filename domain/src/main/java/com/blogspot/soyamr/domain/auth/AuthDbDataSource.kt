package com.blogspot.soyamr.domain.auth

import com.blogspot.soyamr.domain.auth.model.Session
import kotlinx.coroutines.flow.Flow

interface AuthDbDataSource {
    fun observeSession(): Flow<Session>
    fun getSession(): Session
    fun saveSession(session: Session)
    fun clearSessions()
}
