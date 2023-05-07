package com.blogspot.soyamr.recipes3.presentation.authorization

import com.blogspot.soyamr.recipes3.presentation.common.UiEvent
import com.blogspot.soyamr.recipes3.presentation.common.UiState

data class AuthorizationPhoneNumberState(
    val hasUserAcceptedPolicy: Boolean = false,
    val userPhoneNumber: String = "",
    val errorMessageId: Int? = null,
    val isLoading: Boolean = false,
) : UiState {
    val isLoginButtonEnabled: Boolean
        get() = hasUserAcceptedPolicy && userPhoneNumber.isNotEmpty() && errorMessageId == null
}

sealed class AuthorizationPhoneNumberEvent : UiEvent() {

    object NavigateUp : AuthorizationPhoneNumberEvent()
}