package com.affise.attribution.internal.platform

import android.net.Uri
import com.affise.attribution.Affise

object InternalCrossPlatform {

    private fun api() = Affise.getApi()

    @JvmStatic
    fun deeplink(url: String) {
        api()?.deeplinkManager?.handleDeeplink(Uri.parse(url))
    }

    @JvmStatic
    fun start() {
        api()?.sessionManager?.sessionStart()
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