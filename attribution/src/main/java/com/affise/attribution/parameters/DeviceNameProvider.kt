package com.affise.attribution.parameters

import android.app.Application
import android.provider.Settings
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.DEVICE_NAME]
 *
 * @property app to retrieve global settings
 */
class DeviceNameProvider(
    private val app: Application
) : StringPropertyProvider() {

    override val order: Float = 41.0f
    override val key: String = Parameters.DEVICE_NAME

    override fun provide(): String? = Settings.Global
        .getString(app.contentResolver, "device_name")
}