package com.affise.attribution.parameters

import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.AFFISE_DEEPLINK]
 *
 * @property deeplinkClickRepository to retrieve deeplink
 */
class DeeplinkProvider(
    private val deeplinkClickRepository: DeeplinkClickRepository
) : StringPropertyProvider() {

    override val order: Float = 58.0f
    override val key: String = Parameters.AFFISE_DEEPLINK

    override fun provide(): String = deeplinkClickRepository.getDeeplink() ?: defaultValue
}