package com.affise.attribution.parameters.providers

import android.content.Context
import android.os.Build
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * App version number (Android) [ProviderType.APP_VERSION_RAW]
 *
 * @property context context to retrieve app version from
 * @property logsManager for error logging
 */
class AppVersionRawProvider(
    private val context: Context,
    private val logsManager: LogsManager
) : StringPropertyProvider() {

    override val order: Float = 4.0f
    override val key: ProviderType = ProviderType.APP_VERSION_RAW

    @Suppress("DEPRECATION")
    override fun provide(): String? = try {
        context
            .packageManager
            .getPackageInfo(context.packageName, 0)
            ?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    it.longVersionCode
                } else {
                    it.versionCode
                }
            }
            ?.toString()
    } catch (e: Exception) {
        //log error
        logsManager.addDeviceError(e)
        null
    }
}