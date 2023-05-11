package com.blogspot.soyamr.recipes3.presentation.recipeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.blogspot.soyamr.domain.recipe.ObserveRecipeDetailsParams
import com.blogspot.soyamr.domain.recipe.ObserveRecipeDetailsUseCase
import com.blogspot.soyamr.domain.recipe.UpdateCachedRecipeDetailsParams
import com.blogspot.soyamr.domain.recipe.UpdateCachedRecipeDetailsUseCase
import com.blogspot.soyamr.recipes3.presentation.common.BaseViewModel
import com.blogspot.soyamr.recipes3.presentation.common.UiConverters.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val updateRecipeDetailsUseCase: UpdateCachedRecipeDetailsUseCase,
    private val observeRecipeDetailsUseCase: ObserveRecipeDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RecipeDetailsUiState, RecipeDetailsEvent>(RecipeDetailsUiState()) {

    private val args = RecipeDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        updateRecipeDetails(isForceUpdate = false)
        observeRecipeDetails()
    }

    private fun observeRecipeDetails() {
        observeRecipeDetailsUseCase(ObserveRecipeDetailsParams(id = args.id))
            .filterNotNull()
            .onEach { recipe ->
                updateUiState {
                    copy(
                        recipe = recipe.toUi(),
                        images = recipe.images,
                        similar = recipe.similar.map { it.toUi() },
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateRecipeDetails(isForceUpdate: Boolean) {
        updateRecipeDetailsUseCase(
            UpdateCachedRecipeDetailsParams(
                id = args.id,
                isForceUpdate = isForceUpdate
            )
        )
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .onEach { result -> result.onFailure { handleError(it) } }
            .launchIn(viewModelScope)
    }

    fun shareRecipe() {
        sendUiEvent(RecipeDetailsEvent.ShareRecipe(args.id))
    }
}