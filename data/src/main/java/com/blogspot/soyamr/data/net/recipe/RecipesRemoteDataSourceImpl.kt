package com.blogspot.soyamr.data.net.recipe

import com.blogspot.soyamr.data.net.common.NetConverters.toDomain
import com.blogspot.soyamr.domain.recipe.RecipesRemoteDataSource
import com.blogspot.soyamr.domain.recipe.model.Recipe
import com.blogspot.soyamr.domain.recipe.model.RecipeDetails
import javax.inject.Inject

class RecipesRemoteDataSourceImpl @Inject constructor(
    private val recipeApi: RecipeApi
) : RecipesRemoteDataSource {
    override suspend fun getRecipeList(): List<Recipe> {
        return recipeApi.getRecipes().recipes?.map { it.toDomain() }.orEmpty()
    }

    override suspend fun getRecipeDetails(id: String): RecipeDetails {
        return recipeApi.getRecipeDetails(id).recipe.toDomain()
    }

}