package com.blogspot.soyamr.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Suppress("InjectDispatcher")
interface LocalFlowUseCase<in Input, Output> {
    /**
     * Executes the flow on Dispatchers.IO
     * Doesn't handle exception, since it's not likely to happen while collecting from local source
     */
    operator fun invoke(param: Input): Flow<Output> =
        execute(param).flowOn(Dispatchers.IO)

    fun execute(param: Input): Flow<Output>
}

@Suppress("InjectDispatcher")
interface FlowUseCase<in Input, Output> {
    /**
     * Executes the flow on Dispatchers.IO and wraps exceptions inside it into Result
     */
    operator fun invoke(param: Input): Flow<Result<Output>> =
        execute(param)
            .map { r -> Result.success(r) }
            .catch { e -> emit(Result.failure(e)) }
            .flowOn(Dispatchers.IO)

    fun execute(param: Input): Flow<Output>
}

@Suppress("InjectDispatcher")
interface SuspendedUseCase<in Input, Output> {
    suspend operator fun invoke(param: Input): Output =
        withContext(Dispatchers.IO) { execute(param) }

    suspend fun execute(param: Input): Output
}

@Suppress("InjectDispatcher")
interface BlockingUseCase<in Input, Output> {
    operator fun invoke(param: Input): Output =
        runBlocking { execute(param).flowOn(Dispatchers.IO).first() }

    suspend fun execute(param: Input): Flow<Output>
}
