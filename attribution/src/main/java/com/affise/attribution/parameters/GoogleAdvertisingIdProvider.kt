package com.affise.attribution.parameters

import com.affise.attribution.advertising.AdvertisingIdManager
import com.affise.attribution.parameters.base.StringPropertyProvider

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

    override fun provide(): String? = advertisingIdManager.getAdvertisingId()
}