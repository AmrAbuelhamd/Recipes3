package com.blogspot.soyamr.recipes3.presentation.recipeList.models

import com.blogspot.soyamr.recipes3.presentation.common.models.Identity

data class RecipeUi(
    override val id: String,
    val name: String,
    val image: String,
    val lastUpdated: String,
    val description: String,
    val instructions: String,
    val difficulty: Int,
) : Identity<String>