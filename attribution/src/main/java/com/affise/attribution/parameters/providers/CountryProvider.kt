package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.Locale

/**
 *  Provider for parameter [ProviderType.COUNTRY]
 */
class CountryProvider : StringPropertyProvider() {

    override val order: Float = 39.0f
    override val key: ProviderType = ProviderType.COUNTRY

    override fun provide(): String? = Locale.getDefault().country
}