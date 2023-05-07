package com.blogspot.soyamr.recipes3.presentation.common

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<VB : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding as VB

    abstract val TAG: String

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun VB.setupViews()

    inline fun <reified T> show(
        fragmentManager: FragmentManager,
        param: T? = null,
    ) where T : Parcelable {
        arguments = Bundle().apply { putParcelable("param", param) }
        show(fragmentManager, TAG)
    }

    fun show(fragmentManager: FragmentManager) = show(fragmentManager, TAG)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}