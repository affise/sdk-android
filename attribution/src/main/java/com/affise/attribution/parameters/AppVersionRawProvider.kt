package com.affise.attribution.parameters

import android.content.Context
import android.os.Build
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * App version number (Android) [Parameters.APP_VERSION_RAW]
 *
 * @property context context to retrieve app version from
 * @property logsManager for error logging
 */
class AppVersionRawProvider(
    private val context: Context,
    private val logsManager: LogsManager
) : StringPropertyProvider() {

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