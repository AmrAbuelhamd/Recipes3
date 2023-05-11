package com.blogspot.soyamr.domain.recipe

import com.blogspot.soyamr.domain.recipe.model.Recipe
import com.blogspot.soyamr.domain.recipe.model.RecipeDetails

interface RecipesRemoteDataSource {
    suspend fun getRecipeList(): List<Recipe>
    suspend fun getRecipeDetails(id: String): RecipeDetails
}