package com.affise.app.extensions

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager

fun Context.convertDpToPixels(dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    ).toInt()
}

fun Activity.hideKeyboard() {
    val viewFocus = currentFocus

    val token = viewFocus?.windowToken
        ?: window.decorView.rootView.windowToken
        ?: return

    val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?: return

    imm.hideSoftInputFromWindow(token, 0)
}