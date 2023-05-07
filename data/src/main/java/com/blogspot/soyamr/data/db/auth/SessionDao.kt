package com.blogspot.soyamr.data.db.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blogspot.soyamr.data.db.auth.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Query("SELECT * FROM Sessions ORDER BY id DESC LIMIT 1")
    fun observeLastSession(): Flow<SessionEntity?>

    @Query("SELECT * FROM Sessions ORDER BY id DESC LIMIT 1")
    fun getLastSession(): SessionEntity?

    @Insert(entity = SessionEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun createSession(entity: SessionEntity): Long

    @Query("DELETE FROM Sessions")
    fun clearSessions()
}
