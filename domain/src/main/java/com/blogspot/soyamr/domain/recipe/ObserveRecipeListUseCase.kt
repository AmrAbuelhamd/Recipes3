package com.blogspot.soyamr.domain.recipe

import com.blogspot.soyamr.domain.LocalFlowUseCase
import com.blogspot.soyamr.domain.recipe.model.Recipe
import com.blogspot.soyamr.domain.recipe.model.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveRecipeListUseCase : LocalFlowUseCase<ObserveRecipeParams, List<Recipe>>

class ObserveRecipeListUseCaseImpl @Inject constructor(
    private val dataSource: RecipesDbDataSource,
) : ObserveRecipeListUseCase {

    override fun execute(param: ObserveRecipeParams): Flow<List<Recipe>> =
        dataSource.observeRecipes(param.keyword, param.sortType)
}

data class ObserveRecipeParams(
    val keyword: String,
    val sortType: SortType = SortType.NONE
)