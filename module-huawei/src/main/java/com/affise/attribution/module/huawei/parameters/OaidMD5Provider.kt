package com.affise.attribution.module.huawei.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.module.huawei.oaid.OaidManager
import com.affise.attribution.parameters.ProviderType

/**
 * Provider for parameter [ProviderType.OAID_MD5]
 *
 * @property oaidManager to retrieve oaid
 * @property converter for convert oaid address to md5
 */
class OaidMD5Provider(
    private val oaidManager: OaidManager,
    private val converter: Converter<@JvmSuppressWildcards String, @JvmSuppressWildcards String>?
) : StringPropertyProvider() {
    override val order: Float = 31.61f
    override val key: ProviderType = ProviderType.OAID_MD5

    override fun provide(): String? = oaidManager.getOaid()?.let { converter?.convert(it) }
}