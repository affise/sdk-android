package com.affise.attribution.internal.platform

import android.net.Uri
import com.affise.attribution.Affise
import com.affise.attribution.AffiseApi

object InternalCrossPlatform {

    private val api: AffiseApi?
        get() = Affise.api

    @JvmStatic
    fun deeplink(url: String) {
        api?.deeplinkManager?.handleDeeplink(Uri.parse(url))
    }

    @JvmStatic
    fun start() {
        api?.sessionManager?.sessionStart()
    }

    @JvmStatic
    fun react() {
        InternalSdkPlatform.react()
    }

    @JvmStatic
    fun flutter() {
        InternalSdkPlatform.flutter()
    }

    @JvmStatic
    fun unity() {
        InternalSdkPlatform.unity()
    }
}