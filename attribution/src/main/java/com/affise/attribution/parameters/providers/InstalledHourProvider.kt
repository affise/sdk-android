package com.affise.attribution.parameters.providers

import android.content.Context
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider
import java.util.Calendar
import java.util.Date

/**
 * Provider for parameter [ProviderType.INSTALLED_HOUR]
 *
 * @property context to retrieve package manager from
 */
class InstalledHourProvider(
    private val context: Context
) : LongPropertyProvider() {

    override val order: Float = 8.0f
    override val key: ProviderType = ProviderType.INSTALLED_HOUR

    @Suppress("DEPRECATION")
    override fun provide(): Long? = context
        .packageManager
        .getPackageInfo(context.packageName, 0)
        ?.firstInstallTime
        ?.stripTimestampToHours()

    private fun Long.stripTimestampToHours() = Calendar.getInstance().apply {
        //Set first install time
        time = Date(this@stripTimestampToHours)

        //Remove millisecond
        set(Calendar.MILLISECOND, 0)

        //Remove second
        set(Calendar.SECOND, 0)

        //Remove minute
        set(Calendar.MINUTE, 0)
    }.timeInMillis
}