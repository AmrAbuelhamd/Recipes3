package com.blogspot.soyamr.domain.recipe

import com.blogspot.soyamr.domain.FlowUseCase
import com.blogspot.soyamr.domain.helpers.CacheExpirationHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateCachedRecipeListUseCase : FlowUseCase<UpdateRecipeListParams, Unit>

@Suppress("LongParameterList")
class UpdateCachedRecipeListUseCaseImpl @Inject constructor(
    private val cacheExpirationHelper: CacheExpirationHelper,
    private val recipesDbDataSource: RecipesDbDataSource,
    private val recipesRemoteDataSource: RecipesRemoteDataSource,
) : UpdateCachedRecipeListUseCase {

    override fun execute(param: UpdateRecipeListParams): Flow<Unit> = flow {
        if (param.isForceUpdate || cacheExpirationHelper.isCachedListExpired()) {
            val recipes = recipesRemoteDataSource.getRecipeList()
            recipesDbDataSource.updateRecipes(recipes)
            cacheExpirationHelper.updateListLastUpdateTimeStamp()
        }
        emit(Unit)
    }
}

data class UpdateRecipeListParams(
    val isForceUpdate: Boolean,
)