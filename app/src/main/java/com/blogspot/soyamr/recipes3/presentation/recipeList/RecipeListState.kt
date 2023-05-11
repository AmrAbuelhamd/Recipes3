package com.blogspot.soyamr.recipes3.presentation.recipeList

import com.blogspot.soyamr.recipes3.presentation.common.UiEvent
import com.blogspot.soyamr.recipes3.presentation.common.UiState
import com.blogspot.soyamr.recipes3.presentation.recipeList.models.RecipeUi

data class RecipeListState(
    val recipes: List<RecipeUi> = emptyList(),
    val isListLoading: Boolean = true,
) : UiState

sealed class RecipeListEvent : UiEvent() {
    object HideSwipeLayoutLoading : RecipeListEvent()
}