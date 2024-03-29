package com.affise.attribution.parameters.providers

import android.content.Context
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.APP_VERSION]
 * App version number (Android)
 *
 * @property context to retrieve app version name
 * @property logsManager for error logging
 */
class AppVersionProvider(
    private val context: Context,
    private val logsManager: LogsManager
) : StringPropertyProvider() {

    override val order: Float = 3.0f
    override val key: ProviderType = ProviderType.APP_VERSION

    @Suppress("DEPRECATION")
    override fun provide(): String? = try {
        context.packageManager
            .getPackageInfo(context.packageName, 0)
            ?.versionName
    } catch (e: Exception) {
        //log error
        logsManager.addDeviceError(e)

        null
    }
}