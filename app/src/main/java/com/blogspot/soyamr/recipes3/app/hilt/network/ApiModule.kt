package com.blogspot.soyamr.recipes3.app.hilt.network

import com.blogspot.soyamr.data.net.common.Network
import com.blogspot.soyamr.data.net.recipe.RecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun bindAuthApi(retrofit: Retrofit): RecipeApi = Network.getApi(retrofit)
}