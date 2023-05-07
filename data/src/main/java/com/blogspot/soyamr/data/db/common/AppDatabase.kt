package com.blogspot.soyamr.data.db.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blogspot.soyamr.data.db.auth.SessionDao
import com.blogspot.soyamr.data.db.auth.SessionEntity

@Database(
    version = 1,
    entities = [
        SessionEntity::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    public abstract fun sessionDao(): SessionDao
}