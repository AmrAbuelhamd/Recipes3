package com.blogspot.soyamr.recipes3.app.hilt.db

import android.content.Context
import com.blogspot.soyamr.data.db.common.AppDatabase
import com.blogspot.soyamr.data.db.common.Database
import com.blogspot.soyamr.data.db.common.DbTransactionProcessorImpl
import com.blogspot.soyamr.domain.common.DbTransactionProcessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Database.build(context)

    @Provides
    fun provideDbTransactionProcessor(
        database: AppDatabase
    ): DbTransactionProcessor = DbTransactionProcessorImpl(database)
}