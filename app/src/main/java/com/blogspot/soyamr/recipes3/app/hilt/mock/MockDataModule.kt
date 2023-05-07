package com.blogspot.soyamr.recipes3.app.hilt.mock

import com.blogspot.soyamr.data.db.auth.SessionDao
import com.blogspot.soyamr.data.mockdata.AuthApiMockImpl
import com.blogspot.soyamr.data.mockdata.SessionDaoMockImpl
import com.blogspot.soyamr.data.net.auth.AuthApi
import com.blogspot.soyamr.data.net.common.Mock
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface MockDataModule {

    @Binds
    @Mock
    @Singleton
    fun bindMockAuthApi(impl: AuthApiMockImpl): AuthApi

    @Binds
    @Mock
    @Singleton
    fun bindMockAuthDao(impl: SessionDaoMockImpl): SessionDao
}
