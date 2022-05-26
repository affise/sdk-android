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

    override fun provide(): String = deeplinkClickRepository.getDeeplink() ?: defaultValue
}