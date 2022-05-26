package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import java.util.Calendar

/**
 * Provider for parameter [Parameters.CREATED_TIME]
 */
class CreatedTimeProvider : LongPropertyProvider() {

    override fun provide(): Long = Calendar.getInstance().apply {
        //Remove millisecond
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}