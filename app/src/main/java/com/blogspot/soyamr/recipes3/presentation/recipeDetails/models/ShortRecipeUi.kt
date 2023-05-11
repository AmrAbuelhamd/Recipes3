package com.blogspot.soyamr.recipes3.presentation.recipeDetails.models

import com.blogspot.soyamr.recipes3.presentation.common.models.Identity

data class ShortRecipeUi(
    override val id: String,
    val name: String,
    val image: String
) : Identity<String>
