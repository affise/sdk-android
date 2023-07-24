package com.affise.attribution

import android.net.Uri
import com.affise.attribution.platform.SdkPlatform

object InternalCrossPlatform {

    private fun api() = Affise.getApi()

    @JvmStatic
    fun deeplink(url: String?) {
        url ?: return
        api()?.deeplinkManager?.handleDeeplink(Uri.parse(url))
    }

    @JvmStatic
    fun start() {
        api()?.sessionManager?.sessionStart()
    }

    @JvmStatic
    fun react() {
        SdkPlatform.react()
    }

    @JvmStatic
    fun flutter() {
        SdkPlatform.flutter()
    }

    @JvmStatic
    fun unity() {
        SdkPlatform.unity()
    }
}