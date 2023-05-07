package com.blogspot.soyamr.recipes3.presentation.common.utils

import android.view.View

fun View.setSafeOnClickListener(delayMillis: Long = 1000, onSafeClick: (View) -> Unit) {
    setOnClickListener {
        this.isEnabled = false
        onSafeClick(this)
        postDelayed({ isEnabled = true }, delayMillis)
    }
}
