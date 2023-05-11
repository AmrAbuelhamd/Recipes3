package com.blogspot.soyamr.recipes3.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.badoo.mvicore.ModelWatcher
import com.blogspot.soyamr.recipes3.R
import com.blogspot.soyamr.recipes3.presentation.common.extentions.observeFlow
import com.blogspot.soyamr.recipes3.presentation.common.extentions.showSnackBar
import kotlinx.coroutines.flow.mapNotNull

abstract class BaseFragment<VB : ViewBinding, S : UiState, E : UiEvent> : Fragment() {

    abstract val viewModel: BaseViewModel<S, E>

    private var _binding: VB? = null
    protected val binding get() = _binding as VB

    abstract val stateRenderer: ModelWatcher<S>

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun VB.setupViews()

    abstract fun E.handleEvent()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAppEvents()
        observeUiState()
        binding.setupViews()
    }

    private fun observeUiState() {
        observeFlow(viewModel.uiState, stateRenderer::invoke)
        observeFlow(viewModel.uiEvents.mapNotNull { it.firstOrNull() }) { event ->
            event.handleEvent()
            viewModel.removeEvent(event.id)
        }
    }

    private fun observeAppEvents() {
        observeFlow(viewModel.appWideEvents.mapNotNull { it.firstOrNull() }) { event ->
            event.handleAppWideEvent()
            viewModel.removeEvent(event.id)
        }
    }

    protected fun AppEvent.handleAppWideEvent() {
        val message = when (this) {
            is AppEvent.ErrorMessage -> this.message
            is AppEvent.Unknown -> getString(R.string.general_error_text_unknown)
            is AppEvent.NoInternet -> getString(R.string.general_error_text_no_internet) // TODO: show full screen dialog if there is no internet.
        }
        showSnackBar(message = message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stateRenderer.clear()
        _binding = null
    }
}