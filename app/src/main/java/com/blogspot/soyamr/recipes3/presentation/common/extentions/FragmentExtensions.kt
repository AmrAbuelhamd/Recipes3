package com.blogspot.soyamr.recipes3.presentation.common.extentions

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.blogspot.soyamr.recipes3.R
import com.blogspot.soyamr.recipes3.presentation.common.utils.AppSnackBarUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.saket.bettermovementmethod.BetterLinkMovementMethod

fun <T> Fragment.observeFlow(flow: Flow<T>, action: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(action)
        }
    }
}

fun Fragment.showSnackBar(
    message: String,
    buttonTitle: String? = null,
    @DrawableRes startDrawableId: Int? = null,
    onClick: (() -> Unit)? = null,
    length: Int = Snackbar.LENGTH_LONG,
) {
    val viewGroup = view ?: return
    AppSnackBarUtils.showSnackBar(
        viewGroup = viewGroup,
        message = message,
        buttonText = buttonTitle,
        startDrawableId = startDrawableId,
        onClick = onClick,
        length = length
    )
}

fun Fragment.showSnackBar(
    @StringRes messageStringId: Int,
    @StringRes buttonTitleId: Int? = null,
    @DrawableRes startDrawableId: Int? = null,
    onClick: (() -> Unit)? = null,
    length: Int = Snackbar.LENGTH_LONG,
) {
    showSnackBar(
        message = getString(messageStringId),
        buttonTitle = buttonTitleId?.let { getString(it) },
        startDrawableId = startDrawableId,
        onClick = onClick,
        length = length,
    )
}

fun Fragment.openSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", requireContext().packageName, null)
    }.also(::startActivity)
}

fun Fragment.getDrawable(@DrawableRes drawableId: Int) =
    ContextCompat.getDrawable(requireContext(), drawableId)

fun Fragment.getColor(@ColorRes colorId: Int) = ContextCompat.getColor(requireContext(), colorId)

/*
* @param stringArrayId
* receives string array of 3 elements:
* 1. complete text
* 2. clickable text
* 3. url
* ex:
    <string-array name="privacy_agreement">
        <item>I agree to the rules and regulations</item>
        <item>rules and regulations</item>
        <item>https://example.com/rules-and-regulations</item>
    </string-array>
* */

fun TextView.setClickableText(
    clickableText: String,
    url: String,
    underlined: Boolean = false,
    @ColorRes clickableColor: Int = R.color.primary_wave_100,
    action: ((url: String) -> Unit)? = null,
) {
    val spannableString = SpannableString(this.text)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(textView: View) {
            action?.invoke(url)
        }

        override fun updateDrawState(drawState: TextPaint) {
            super.updateDrawState(drawState)
            drawState.isUnderlineText = underlined
        }
    }
    val startingPosition = spannableString.indexOf(clickableText)
    val endingPosition: Int = startingPosition + clickableText.length
    spannableString.setSpan(
        ForegroundColorSpan(
            ContextCompat.getColor(context, clickableColor)
        ),
        startingPosition,
        endingPosition,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannableString.setSpan(
        clickableSpan,
        startingPosition,
        endingPosition,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = spannableString
    this.movementMethod = BetterLinkMovementMethod.getInstance()
    this.highlightColor = Color.TRANSPARENT
}

fun Fragment.openLinkInBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}