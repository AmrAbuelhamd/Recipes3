package com.blogspot.soyamr.recipes3.app.hilt.db

import com.blogspot.soyamr.data.db.common.AppDatabase
import com.blogspot.soyamr.data.db.recipe.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun bindRecipeDao(db: AppDatabase): RecipeDao = db.recipeDao()
}