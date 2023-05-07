package com.blogspot.soyamr.recipes3.presentation.common

import androidx.lifecycle.ViewModel
import com.blogspot.soyamr.domain.common.model.CommonBackendFailure
import com.blogspot.soyamr.domain.common.model.NoInternetFailure
import com.blogspot.soyamr.domain.common.model.UnknownFailure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S : UiState, E : UiEvent>(initialState: S) : ViewModel() {

    private val _appWideEvents = MutableStateFlow(emptyList<AppEvent>())
    internal val appWideEvents = _appWideEvents.asStateFlow()

    private val _uiState = MutableStateFlow(initialState)
    internal val uiState = _uiState.asStateFlow()

    private val _uiEvents = MutableStateFlow(emptyList<E>())
    internal val uiEvents = _uiEvents.asStateFlow()

    val currentState: S
        get() = _uiState.value

    fun updateUiState(block: S.() -> S) {
        _uiState.update { block(it) }
    }

    fun removeEvent(eventId: String) {
        _uiEvents.update { it.filterNot { it.id == eventId } }
    }

    fun sendUiEvent(event: E) {
        _uiEvents.update { it + event }
    }

    fun handleError(throwable: Throwable) {
        val error = when (throwable) {
            is CommonBackendFailure -> AppEvent.ErrorMessage(throwable.apiError?.message ?: "")
            is UnknownFailure -> AppEvent.Unknown
            is NoInternetFailure -> AppEvent.NoInternet
            else -> AppEvent.Unknown
        }
        _appWideEvents.update { it + error }
    }
}