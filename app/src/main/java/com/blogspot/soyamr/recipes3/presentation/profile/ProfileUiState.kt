package com.blogspot.soyamr.recipes3.presentation.profile

import com.blogspot.soyamr.recipes3.presentation.common.UiEvent
import com.blogspot.soyamr.recipes3.presentation.common.UiState

data class ProfileUiState(
    val phoneNumber: String? = null,
    val isLoading: Boolean = true,
    val isUserAuthorized: Boolean = false,
) : UiState

sealed class ProfileEvent : UiEvent() {
    object NavigateToAuthorization : ProfileEvent()
}