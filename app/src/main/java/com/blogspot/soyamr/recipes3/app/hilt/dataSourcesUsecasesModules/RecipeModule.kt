package com.blogspot.soyamr.recipes3.app.hilt.dataSourcesUsecasesModules;

import com.blogspot.soyamr.data.db.recipe.RecipesDbDataSourceImpl
import com.blogspot.soyamr.data.net.recipe.RecipesRemoteDataSourceImpl
import com.blogspot.soyamr.domain.recipe.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RecipeModule {
    // region data sources
    @Binds
    fun bindRecipeRemoteDataSource(impl: RecipesRemoteDataSourceImpl): RecipesRemoteDataSource

    @Binds
    fun bindRecipeDbDataSource(impl: RecipesDbDataSourceImpl): RecipesDbDataSource
    // endregion

    // region use cases
    @Binds
    fun bindObserveRecipeDetailsUseCase(impl: ObserveRecipeDetailsUseCaseImpl): ObserveRecipeDetailsUseCase

    @Binds
    fun bindObserveRecipeListUseCase(impl: ObserveRecipeListUseCaseImpl): ObserveRecipeListUseCase

    @Binds
    fun bindUpdateCachedRecipeDetailsUseCase(impl: UpdateCachedRecipeDetailsUseCaseImpl): UpdateCachedRecipeDetailsUseCase

    @Binds
    fun bindUpdateCachedRecipeListUseCase(impl: UpdateCachedRecipeListUseCaseImpl): UpdateCachedRecipeListUseCase
    // endregion
}
