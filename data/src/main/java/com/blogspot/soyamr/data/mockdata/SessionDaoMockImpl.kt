package com.blogspot.soyamr.data.mockdata

import com.blogspot.soyamr.data.db.auth.SessionDao
import com.blogspot.soyamr.data.db.auth.SessionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SessionDaoMockImpl @Inject constructor() : SessionDao {
    private val session = MutableStateFlow<SessionEntity?>(null)

    override fun observeLastSession(): Flow<SessionEntity?> {
        return session
    }

    override fun getLastSession(): SessionEntity? {
        return session.value
    }

    override fun createSession(entity: SessionEntity): Long {
        session.value = entity
        return entity.id
    }

    override fun clearSessions() {
        session.value = null
    }
}