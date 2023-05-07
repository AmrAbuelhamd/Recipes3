package com.blogspot.soyamr.recipes3.presentation.authorization

import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.badoo.mvicore.modelWatcher
import com.blogspot.soyamr.recipes3.R
import com.blogspot.soyamr.recipes3.databinding.FragmentAuthorizationBinding
import com.blogspot.soyamr.recipes3.presentation.common.BaseFragment
import com.blogspot.soyamr.recipes3.presentation.common.extentions.changeErrorState
import com.blogspot.soyamr.recipes3.presentation.common.extentions.isLoading
import com.blogspot.soyamr.recipes3.presentation.common.extentions.setClickableText
import com.blogspot.soyamr.recipes3.presentation.common.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment :
    BaseFragment<FragmentAuthorizationBinding, AuthorizationPhoneNumberState, AuthorizationPhoneNumberEvent>() {

    override val viewModel: AuthorizationViewModel by viewModels()

    private val russianCountryCodeIso = "RU"

    override val stateRenderer = modelWatcher {
        AuthorizationPhoneNumberState::hasUserAcceptedPolicy { isChecked ->
            binding.chkPolicy.isChecked = isChecked
        }
        AuthorizationPhoneNumberState::errorMessageId { errorMessageId ->
            binding.ilPhoneNumber.changeErrorState(errorMessageId?.let(::getString))
        }
        AuthorizationPhoneNumberState::isLoginButtonEnabled { isEnabled ->
            binding.btnLogin.isEnabled = isEnabled
        }
        AuthorizationPhoneNumberState::isLoading { isLoading ->
            binding.progressBar.isLoading = isLoading
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAuthorizationBinding {
        return FragmentAuthorizationBinding.inflate(inflater, container, false)
    }

    override fun AuthorizationPhoneNumberEvent.handleEvent() {
        when (this) {
            is AuthorizationPhoneNumberEvent.NavigateUp -> {
                findNavController().popBackStack(R.id.authorization_nav_graph, true)
            }
        }
    }

    override fun FragmentAuthorizationBinding.setupViews() {
        with(binding) {
            tvPolicy.setClickableText(
                clickableText = getString(R.string.authorization_link_policy),
                url = ""
            ) {}
            tvPolicy.setClickableText(
                clickableText = getString(R.string.authorization_company_rules),
                url = ""
            ) {}
            etPhoneNumber.doAfterTextChanged { text ->
                viewModel.updatePhoneNumberValue(text.toString())
            }
            btnLogin.setSafeOnClickListener {
                viewModel.registerByPhone()
            }
            btnNavigateBack.setSafeOnClickListener {
                findNavController().popBackStack(R.id.authorization_nav_graph, true)
            }
            chkPolicy.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updatePolicyAgreementCheckboxState(isChecked)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etPhoneNumber.addTextChangedListener(
            PhoneNumberFormattingTextWatcher(
                russianCountryCodeIso
            )
        )
    }
}