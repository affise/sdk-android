package com.affise.attribution.module.advertising.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.module.advertising.advertising.AdvertisingIdManager
import com.affise.attribution.parameters.ProviderType

/**
 * Provider for parameter [ProviderType.GAID_ADID_MD5]
 *
 * provides google advertising id encoded with md5 or "unknown" if not set
 *
 * @property advertisingIdManager manager to fetch advertising id from
 * @property md5Converter to convert advertising id to md5
 */
class GoogleAdvertisingIdMd5Provider(
    private val advertisingIdManager: AdvertisingIdManager,
    private val md5Converter: Converter<@JvmSuppressWildcards String, @JvmSuppressWildcards String>
) : StringPropertyProvider() {

    override val order: Float = 31.4f
    override val key: ProviderType = ProviderType.GAID_ADID_MD5

    override fun provide(): String? = advertisingIdManager
        .getAdvertisingId()
        ?.let(md5Converter::convert)
}