package com.blogspot.soyamr.recipes3.app.hilt.utils

import com.blogspot.soyamr.domain.helpers.CacheExpirationHelper
import com.blogspot.soyamr.domain.helpers.CacheExpirationHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UtilsModule {

    @Binds
    fun getCacheExpirationHelper(impl: CacheExpirationHelperImpl): CacheExpirationHelper
}