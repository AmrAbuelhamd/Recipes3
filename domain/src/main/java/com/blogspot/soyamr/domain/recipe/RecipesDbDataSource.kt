package com.blogspot.soyamr.domain.recipe

import com.blogspot.soyamr.domain.recipe.model.Recipe
import com.blogspot.soyamr.domain.recipe.model.RecipeDetails
import com.blogspot.soyamr.domain.recipe.model.SortType
import kotlinx.coroutines.flow.Flow

interface RecipesDbDataSource {
    fun observeRecipes(keyword: String, sortType: SortType): Flow<List<Recipe>>
    fun observeRecipeDetails(id: String): Flow<RecipeDetails?>

    suspend fun updateRecipes(recipes: List<Recipe>)
    suspend fun updateRecipeDetails(recipeDetails: RecipeDetails)
}
