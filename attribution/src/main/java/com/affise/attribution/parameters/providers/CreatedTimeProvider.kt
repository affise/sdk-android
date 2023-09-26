package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider
import java.util.Calendar

/**
 * Provider for parameter [ProviderType.CREATED_TIME]
 */
class CreatedTimeProvider : LongPropertyProvider() {

    override val order: Float = 18.0f
    override val key: ProviderType = ProviderType.CREATED_TIME

    override fun provide(): Long = Calendar.getInstance().apply {
        //Remove millisecond
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}