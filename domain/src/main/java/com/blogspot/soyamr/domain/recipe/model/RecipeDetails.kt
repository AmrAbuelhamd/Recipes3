package com.blogspot.soyamr.domain.recipe.model

class RecipeDetails(
    val uuid: String,
    val name: String,
    val images: List<String>,
    val lastUpdatedOnServer: Long,
    val lastUpdatedInCache: Long,
    val description: String?,
    val instructions: String,
    val difficulty: Int,
    val similar: List<ShortRecipe>
)