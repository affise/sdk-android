package com.affise.attribution.internal.callback

interface OnAffiseCallback {
    fun handleCallback(name: String, data: String?): String?
}