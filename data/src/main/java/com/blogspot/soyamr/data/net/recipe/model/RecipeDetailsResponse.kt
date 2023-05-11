package com.blogspot.soyamr.data.net.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailsResponseWrapper(
    val recipe: RecipeDetailsResponse? = null
)

@Serializable
data class RecipeDetailsResponse(
    val uuid: String? = null,
    val name: String? = null,
    val images: List<String>? = null,
    val lastUpdated: Long? = null,
    val description: String? = null,
    val instructions: String? = null,
    val difficulty: Int? = null,
    val similar: List<ShortRecipeResponse>? = null
)