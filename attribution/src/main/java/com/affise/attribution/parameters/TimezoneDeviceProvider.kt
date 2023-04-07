package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

/**
 * Provider for parameter [Parameters.TIMEZONE_DEV]
 */
class TimezoneDeviceProvider : StringPropertyProvider() {

    override val order: Float = 51.0f
    override val key: String = Parameters.TIMEZONE_DEV

    /**
     * Returns timezone formatted in UTC template, for ex: UTC+0200
     */
    override fun provide(): String = Calendar.getInstance().let {
        it.timeZone.getOffset(it.timeInMillis).toOffsetStr()
    }

    /**
     * Get offset string from int value
     * @return offset string
     */
    private fun Int.toOffsetStr(): String {
        //Convert to minutes
        val inMinutes = TimeUnit.MILLISECONDS.toMinutes(this.toLong())

        //Get sign
        val sign = if (inMinutes < 0) "-" else "+"

        //Get hours
        val hours = inMinutes.div(60).format()

        //Get minutes
        val minutes = inMinutes.rem(60).format()

        //Generate offset string
        return "UTC$sign$hours$minutes"
    }

    /**
     * Formatting value to length = 2
     */
    private fun Long.format() = absoluteValue
        .toString()
        .padStart(2, '0')
}