package com.blogspot.soyamr.domain.common

interface DbTransactionProcessor {
    suspend fun runInTransaction(body: suspend () -> Unit)
}