package com.blogspot.soyamr.recipes3.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.badoo.mvicore.modelWatcher
import com.blogspot.soyamr.recipes3.R
import com.blogspot.soyamr.recipes3.databinding.FragmentProfileBinding
import com.blogspot.soyamr.recipes3.presentation.common.BaseFragment
import com.blogspot.soyamr.recipes3.presentation.common.extentions.isLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileUiState, ProfileEvent>() {
    override val viewModel: ProfileViewModel by viewModels()

    override val stateRenderer = modelWatcher {
        ProfileUiState::isLoading { isLoading ->
            binding.progressBar.isLoading = isLoading
        }
        ProfileUiState::phoneNumber { phoneNumber ->
            binding.tvPhoneNumber.text = phoneNumber
        }
        ProfileUiState::isUserAuthorized { isUserAuthorized ->
            binding.btnLogin.text = if (isUserAuthorized) {
                getString(R.string.authorization_logout)
            } else {
                getString(R.string.authorization_login)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun ProfileEvent.handleEvent() {
        when (this) {
            is ProfileEvent.NavigateToAuthorization -> {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToAuthorizationNavGraph()
                )
            }
        }
    }


    override fun FragmentProfileBinding.setupViews() {
        btnLogin.setOnClickListener {
            viewModel.changeAuthorizationState()
        }
    }
}