package com.blogspot.soyamr.recipes3.presentation.common.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.blogspot.soyamr.recipes3.databinding.ViewSnackbarBinding
import com.blogspot.soyamr.recipes3.presentation.common.extentions.setDrawableStart

object AppSnackBarUtils {

    fun showSnackBar(
        viewGroup: View,
        message: String,
        buttonText: String? = null,
        @DrawableRes startDrawableId: Int? = null,
        onClick: (() -> Unit)? = null,
        length: Int = Snackbar.LENGTH_LONG,
    ) {
        val binding = ViewSnackbarBinding.inflate(
            /* inflater = */ LayoutInflater.from(viewGroup.context),
            /* parent = */ null,
            /* attachToParent = */ false
        )
        val snackBar = Snackbar.make(viewGroup, String(), length).apply {
            setBackgroundTint(
                ContextCompat.getColor(
                    view.context,
                    android.R.color.transparent
                )
            )
        }
        (snackBar.view as? ViewGroup)?.apply {
            removeAllViews()
            addView(binding.root)
            setPadding(0, 0, 0, 0)
            elevation = 0f
        }
        with(binding) {
            textviewSnackbarMessage.text = message
            textviewSnackbarButton.text = buttonText
            textviewSnackbarButton.isVisible = buttonText != null
            textviewSnackbarButton.setOnClickListener { onClick?.invoke() }
            startDrawableId?.also { binding.textviewSnackbarMessage.setDrawableStart(it) }
        }
        snackBar.show()
    }
}