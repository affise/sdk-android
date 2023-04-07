package com.affise.attribution.parameters

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.DEVICE_TYPE]
 *
 * @property app to retrieve system service and configuration
 */
class DeviceTypeProvider(
    private val app: Application
) : StringPropertyProvider() {

    override val order: Float = 42.0f
    override val key: String = Parameters.DEVICE_TYPE

    override fun provide() = detectDeviceTypeByUIMode()
        ?: if (isTablet()) "tablet" else "smartphone"

    /**
     * Check configuration if is tablet
     * @return is tablet or not
     */
    private fun isTablet(): Boolean {
        val size = (app.resources.configuration.screenLayout
            and Configuration.SCREENLAYOUT_SIZE_MASK)
        return (size >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    /**
     * Check mode typ if is television or car
     * @return mode type name
     */
    private fun detectDeviceTypeByUIMode(): String? {
        val manager = app.getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager ?: return null

        return when (manager.currentModeType) {
            Configuration.UI_MODE_TYPE_TELEVISION -> "tv"
            Configuration.UI_MODE_TYPE_CAR -> "car"
            else -> null
        }
    }
}