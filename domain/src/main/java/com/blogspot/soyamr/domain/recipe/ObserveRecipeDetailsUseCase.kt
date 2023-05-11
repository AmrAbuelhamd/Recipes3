package com.blogspot.soyamr.domain.recipe

import com.blogspot.soyamr.domain.LocalFlowUseCase
import com.blogspot.soyamr.domain.recipe.model.RecipeDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveRecipeDetailsUseCase : LocalFlowUseCase<ObserveRecipeDetailsParams, RecipeDetails?>

class ObserveRecipeDetailsUseCaseImpl @Inject constructor(
    private val dataSource: RecipesDbDataSource,
) : ObserveRecipeDetailsUseCase {

    override fun execute(param: ObserveRecipeDetailsParams): Flow<RecipeDetails?> =
        dataSource.observeRecipeDetails(param.id)
}

data class ObserveRecipeDetailsParams(
    val id: String,
)