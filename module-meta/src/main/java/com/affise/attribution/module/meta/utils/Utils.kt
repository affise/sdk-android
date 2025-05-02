package com.affise.attribution.module.meta.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri

internal fun Context.resolveAuthority(authority: String): Boolean = this.packageManager.resolveContentProvider(
    authority, 0
) != null

@SuppressLint("UseKtx")
internal fun String.toContentUri(): Uri? = try {
    Uri.parse("content://$this")
} catch (e: Exception) {
    null
}