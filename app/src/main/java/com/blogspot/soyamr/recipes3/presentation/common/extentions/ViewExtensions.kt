package com.blogspot.soyamr.recipes3.presentation.common.extentions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import com.google.android.material.textfield.TextInputLayout

fun View.showKeyboardCompat() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboardCompat() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun TextView.setDrawableStart(@DrawableRes drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        drawable,
        0,
        0,
        0
    )
}

fun TextInputLayout.changeErrorState(error: String?) {
    this.isErrorEnabled = !error.isNullOrBlank()
    this.error = error
}

fun TextView.setTextOrHide(value: String?) {
    isVisible = !value.isNullOrBlank()
    text = value
}

inline var ContentLoadingProgressBar.isLoading: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        if (value) show() else hide()
    }