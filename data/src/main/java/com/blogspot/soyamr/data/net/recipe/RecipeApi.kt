package com.blogspot.soyamr.data.net.recipe

import com.blogspot.soyamr.data.net.recipe.model.*
import retrofit2.http.*

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes(): RecipesListResponseWrapper

    @GET("recipes/{uuid}")
    suspend fun getRecipeDetails(@Path("uuid") uuid: String): RecipeDetailsResponseWrapper
}