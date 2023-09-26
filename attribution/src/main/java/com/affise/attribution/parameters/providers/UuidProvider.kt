package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.utils.generateUUID

/**
 * Provider for parameter [ProviderType.UUID]
 */
class UuidProvider : StringPropertyProvider() {

    override val order: Float = 64.0f
    override val key: ProviderType = ProviderType.UUID

    override fun provide(): String? = generateUUID().toString()
}