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

    override val order: Float = 30.0f
    override val key: String = Parameters.ANDROID_ID

    @SuppressLint("HardwareIds")
    override fun provide(): String? = Settings.Secure.getString(
        app.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}