package com.blogspot.soyamr.domain.recipe

import com.blogspot.soyamr.domain.FlowUseCase
import com.blogspot.soyamr.domain.helpers.CacheExpirationHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateCachedRecipeDetailsUseCase : FlowUseCase<UpdateCachedRecipeDetailsParams, Unit>

@Suppress("LongParameterList")
class UpdateCachedRecipeDetailsUseCaseImpl @Inject constructor(
    private val cacheExpirationHelper: CacheExpirationHelper,
    private val recipesDbDataSource: RecipesDbDataSource,
    private val recipesRemoteDataSource: RecipesRemoteDataSource,
) : UpdateCachedRecipeDetailsUseCase {

    override fun execute(param: UpdateCachedRecipeDetailsParams): Flow<Unit> = flow {
        val item = recipesDbDataSource.observeRecipeDetails(param.id).first()
        if (param.isForceUpdate || cacheExpirationHelper.isCachedItemExpired(item?.lastUpdatedInCache)) {
            val recipe = recipesRemoteDataSource.getRecipeDetails(param.id)
            recipesDbDataSource.updateRecipeDetails(recipe)
        }
        emit(Unit)
    }
}

data class UpdateCachedRecipeDetailsParams(
    val id: String,
    val isForceUpdate: Boolean,
)