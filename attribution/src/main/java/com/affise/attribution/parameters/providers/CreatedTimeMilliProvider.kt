package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.utils.timestamp

/**
 * Provider for parameter [ProviderType.CREATED_TIME_MILLI]
 */
class CreatedTimeMilliProvider : LongPropertyProvider() {

    override val order: Float = 19.0f
    override val key: ProviderType = ProviderType.CREATED_TIME_MILLI

    override fun provide(): Long = timestamp()
}