package com.affise.attribution.parameters

import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.affise.attribution.parameters.base.BooleanPropertyProvider

/**
 * Provider for property [Parameters.DEEPLINK_CLICK]
 *
 * @property deeplinkClickRepository to retrieve network is deeplink
 */
class DeeplinkClickPropertyProvider(
    private val deeplinkClickRepository: DeeplinkClickRepository
) : BooleanPropertyProvider() {

    override fun provide(): Boolean = deeplinkClickRepository.isDeeplinkClick()
}