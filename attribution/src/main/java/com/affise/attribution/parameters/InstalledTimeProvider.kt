package com.affise.attribution.parameters

import android.content.Context
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.base.LongPropertyProvider

/**
 * Provider for parameter [Parameters.INSTALLED_TIME]
 *
 * @property context to retrieve first install time from
 * @property logsManager for error logging
 */
class InstalledTimeProvider(
    private val context: Context,
    private val logsManager: LogsManager
) : LongPropertyProvider() {

    override val order: Float = 6.0f
    override val key: String = Parameters.INSTALLED_TIME

    override fun provide(): Long? = try {
        context
            .packageManager
            .getPackageInfo(context.packageName, 0)
            ?.firstInstallTime
    } catch (e: Exception) {
        //log error
        logsManager.addDeviceError(e)

        null
    }
}