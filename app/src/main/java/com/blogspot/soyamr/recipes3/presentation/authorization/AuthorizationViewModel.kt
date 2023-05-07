package com.blogspot.soyamr.recipes3.presentation.authorization

import androidx.lifecycle.viewModelScope
import com.blogspot.soyamr.domain.auth.LoginParams
import com.blogspot.soyamr.domain.auth.LoginUseCase
import com.blogspot.soyamr.recipes3.R
import com.blogspot.soyamr.recipes3.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val phoneNumberUtil: PhoneNumberUtil,
) : BaseViewModel<AuthorizationPhoneNumberState, AuthorizationPhoneNumberEvent>(
    AuthorizationPhoneNumberState()
) {
    private val minNumberLengthForValidation = 4

    private val russianCountryCodeIso = "RU"

    fun updatePolicyAgreementCheckboxState(isChecked: Boolean) {
        updateUiState { copy(hasUserAcceptedPolicy = isChecked) }
    }

    fun registerByPhone() {
        loginUseCase(LoginParams(currentState.userPhoneNumber))
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .onEach { result ->
                result.onFailure { handleError(it) }
                result.onSuccess {
                    sendUiEvent(AuthorizationPhoneNumberEvent.NavigateUp)
                }
            }
            .launchIn(viewModelScope)
    }

    fun updatePhoneNumberValue(phoneNumber: String) {
        if (phoneNumber.length < minNumberLengthForValidation) return
        val isNumberValid = try {
            phoneNumberUtil.isValidNumberForRegion(
                /* number = */ phoneNumberUtil.parse(phoneNumber, russianCountryCodeIso),
                /* regionCode = */ russianCountryCodeIso
            )
        } catch (e: Exception) {
            false
        }
        if (isNumberValid) {
            updateUiState {
                copy(
                    userPhoneNumber = phoneNumber,
                    errorMessageId = null
                )
            }
        } else {
            updateUiState { copy(errorMessageId = R.string.authorization_incorrect_number) }
        }
    }
}