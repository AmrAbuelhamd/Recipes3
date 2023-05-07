package com.blogspot.soyamr.recipes3.presentation.common

import java.util.*

interface UiState

abstract class UiEvent {
    val id: String = UUID.randomUUID().toString()
}

sealed class AppEvent : UiEvent() {
    data class ErrorMessage(val message: String) : AppEvent()
    object NoInternet : AppEvent()
    object Unknown : AppEvent()
}