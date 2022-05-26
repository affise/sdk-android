package com.affise.attribution.parameters

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.ANDROID_ID]
 *
 * @property app to retrieve contentResolver
 */
class AndroidIdProvider(
    private val app: Application
) : StringPropertyProvider() {

    @SuppressLint("HardwareIds")
    override fun provide(): String? = Settings.Secure.getString(
        app.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}