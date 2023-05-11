package com.blogspot.soyamr.recipes3.presentation.recipeList

import androidx.lifecycle.viewModelScope
import com.blogspot.soyamr.domain.common.Constants
import com.blogspot.soyamr.domain.recipe.ObserveRecipeListUseCase
import com.blogspot.soyamr.domain.recipe.ObserveRecipeParams
import com.blogspot.soyamr.domain.recipe.UpdateCachedRecipeListUseCase
import com.blogspot.soyamr.domain.recipe.UpdateRecipeListParams
import com.blogspot.soyamr.domain.recipe.model.SortType
import com.blogspot.soyamr.recipes3.presentation.common.BaseViewModel
import com.blogspot.soyamr.recipes3.presentation.common.UiConverters.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val updateCachedRecipeListUseCase: UpdateCachedRecipeListUseCase,
    private val observeRecipeListUseCase: ObserveRecipeListUseCase,
) : BaseViewModel<RecipeListState, RecipeListEvent>(
    RecipeListState()
) {

    private val query = MutableStateFlow("")
    private val sortType = MutableStateFlow(SortType.NONE)

    init {
        refreshData(false)
        observeRecipeList()
    }

    fun refreshData(isForceUpdate: Boolean = true) {
        updateCachedRecipeListUseCase(UpdateRecipeListParams(isForceUpdate = isForceUpdate))
            .onCompletion { sendUiEvent(RecipeListEvent.HideSwipeLayoutLoading) }
            .onEach { result -> result.onFailure { handleError(it) } }
            .launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeRecipeList() {
        combine(query, sortType) { query, sortType -> Pair(query, sortType) }
            .filter { (query, _) -> query.length >= Constants.QUERY_MIN_LENGTH || query.isEmpty() }
            .debounce(Constants.SEARCH_DEBOUNCE)
            .distinctUntilChanged()
            .flatMapLatest { (query, sortType) ->
                observeRecipeListUseCase(ObserveRecipeParams(keyword = query, sortType = sortType))
                    .onStart { updateUiState { copy(isListLoading = true) } }
                    .map { list -> list.map { it.toUi() } }
                    .onEach { updateUiState { copy(recipes = it, isListLoading = false) } }
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String?) {
        this.query.value = query.orEmpty()
    }

    fun updateSortType(sortType: SortType) {
        this.sortType.value = sortType
    }
}