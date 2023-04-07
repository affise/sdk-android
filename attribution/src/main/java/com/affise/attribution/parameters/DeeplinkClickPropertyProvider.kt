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

    override val order: Float = 25.0f
    override val key: String = Parameters.DEEPLINK_CLICK

    override fun provide(): Boolean = deeplinkClickRepository.isDeeplinkClick()
}