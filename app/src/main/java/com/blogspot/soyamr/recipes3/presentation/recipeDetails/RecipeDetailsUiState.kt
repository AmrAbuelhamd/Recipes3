package com.blogspot.soyamr.recipes3.presentation.recipeDetails

import com.blogspot.soyamr.recipes3.presentation.common.UiEvent
import com.blogspot.soyamr.recipes3.presentation.common.UiState
import com.blogspot.soyamr.recipes3.presentation.recipeDetails.models.RecipeDetailsUi
import com.blogspot.soyamr.recipes3.presentation.recipeDetails.models.ShortRecipeUi

data class RecipeDetailsUiState(
    val recipe: RecipeDetailsUi? = null,
    val images: List<String> = emptyList(),
    val similar: List<ShortRecipeUi> = emptyList(),
    val isLoading: Boolean = true,
) : UiState

sealed class RecipeDetailsEvent : UiEvent() {
    data class ShareRecipe(val recipeName: String) : RecipeDetailsEvent()
}