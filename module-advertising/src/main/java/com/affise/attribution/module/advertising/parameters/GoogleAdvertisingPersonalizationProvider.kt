package com.affise.attribution.module.advertising.parameters

import com.affise.attribution.module.advertising.advertising.AdvertisingIdManager
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.BooleanPropertyProvider

/**
 * Provider for parameter [ProviderType.AD_PERSONALIZATION]
 *
 * provides google advertising personalization
 *
 * @property advertisingIdManager manager to fetch advertising id personalization
 */
class GoogleAdvertisingPersonalizationProvider(
    private val advertisingIdManager: AdvertisingIdManager
) : BooleanPropertyProvider() {

    override val order: Float = 31.5f
    override val key: ProviderType = ProviderType.AD_PERSONALIZATION

    override fun provide(): Boolean = advertisingIdManager.getAdPersonalization()
}