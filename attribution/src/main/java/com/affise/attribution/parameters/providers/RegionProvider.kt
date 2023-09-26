package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.Locale

/**
 * Provider for parameter [ProviderType.REGION]
 */
class RegionProvider : StringPropertyProvider() {

    override val order: Float = 38.0f
    override val key: ProviderType = ProviderType.REGION

    override fun provide(): String? = Locale.getDefault().country
}