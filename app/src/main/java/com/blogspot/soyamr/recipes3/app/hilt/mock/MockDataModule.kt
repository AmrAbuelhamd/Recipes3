package com.blogspot.soyamr.recipes3.app.hilt.mock

import com.blogspot.soyamr.data.db.recipe.RecipeDao
import com.blogspot.soyamr.data.mockdata.RecipeApiMockImpl
import com.blogspot.soyamr.data.mockdata.RecipeDaoMockImpl
import com.blogspot.soyamr.data.net.common.Mock
import com.blogspot.soyamr.data.net.recipe.RecipeApi
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
    fun bindMockAuthApi(impl: RecipeApiMockImpl): RecipeApi

    @Binds
    @Mock
    @Singleton
    fun bindMockAuthDao(impl: RecipeDaoMockImpl): RecipeDao
}
