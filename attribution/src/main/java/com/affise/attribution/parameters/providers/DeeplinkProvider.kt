package com.affise.attribution.parameters.providers

import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.AFFISE_DEEPLINK]
 *
 * @property deeplinkClickRepository to retrieve deeplink
 */
class DeeplinkProvider(
    private val deeplinkClickRepository: DeeplinkClickRepository
) : StringPropertyProvider() {

    override val order: Float = 58.0f
    override val key: ProviderType = ProviderType.AFFISE_DEEPLINK

    override fun provide(): String = deeplinkClickRepository.getDeeplink() ?: defaultValue
}