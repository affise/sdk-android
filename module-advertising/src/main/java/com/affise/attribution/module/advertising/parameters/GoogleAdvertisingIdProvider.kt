package com.affise.attribution.module.advertising.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.module.advertising.advertising.AdvertisingIdManager

/**
 * Provider for parameter [Parameters.GAID_ADID]
 *
 * provides google advertising id encoded with md5 or "unknown" if not set
 *
 * @property advertisingIdManager manager to fetch advertising id from
 */
class GoogleAdvertisingIdProvider(
    private val advertisingIdManager: AdvertisingIdManager
) : StringPropertyProvider() {

    override val order: Float = 31.3f
    override val key: String = Parameters.GAID_ADID

    override fun provide(): String? = advertisingIdManager.getAdvertisingId()
}