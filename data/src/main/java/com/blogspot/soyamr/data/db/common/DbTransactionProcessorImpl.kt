package com.blogspot.soyamr.data.db.common

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.blogspot.soyamr.domain.common.DbTransactionProcessor

class DbTransactionProcessorImpl(
    private val roomDatabase: RoomDatabase
) : DbTransactionProcessor {
    override suspend fun runInTransaction(body: suspend () -> Unit) {
        roomDatabase.withTransaction(body)
    }
}
