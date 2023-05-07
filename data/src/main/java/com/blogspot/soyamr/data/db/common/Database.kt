package com.blogspot.soyamr.data.db.common

import android.content.Context
import androidx.room.Room

public object Database {
    public fun build(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()
}
