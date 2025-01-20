package com.affise.attribution.utils

import android.net.Uri
import com.affise.attribution.referrer.ReferrerKey

internal fun String.getReferrerValue(key: ReferrerKey?): String? {
    key ?: return null
    return try {
        Uri.parse("https://referrer/?$this")
            .getQueryParameter(key.type)
    } catch (_: Exception) {
        null
    }
}
