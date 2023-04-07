package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import java.util.Calendar

/**
 * Provider for parameter [Parameters.CREATED_TIME_HOUR]
 */
class CreatedTimeHourProvider : LongPropertyProvider() {

    override val order: Float = 20.0f
    override val key: String = Parameters.CREATED_TIME_HOUR

    override fun provide(): Long = Calendar.getInstance().apply {
        //Remove millisecond
        set(Calendar.MILLISECOND, 0)

        //Remove second
        set(Calendar.SECOND, 0)

        //Remove minute
        set(Calendar.MINUTE, 0)
    }.timeInMillis
}