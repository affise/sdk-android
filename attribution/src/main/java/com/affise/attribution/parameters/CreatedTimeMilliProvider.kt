package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.utils.timestamp

/**
 * Provider for parameter [Parameters.CREATED_TIME_MILLI]
 */
class CreatedTimeMilliProvider : LongPropertyProvider() {

    override val order: Float = 19.0f
    override val key: String = Parameters.CREATED_TIME_MILLI

    override fun provide(): Long = timestamp()
}