package com.affise.attribution.module.advertising.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.module.advertising.oaid.OaidManager

/**
 * Provider for parameter [Parameters.OAID_MD5]
 *
 * @property oaidManager to retrieve oaid
 * @property converter for convert oaid address to md5
 */
class OaidMD5Provider(
    private val oaidManager: OaidManager,
    private val converter: Converter<@JvmSuppressWildcards String, @JvmSuppressWildcards String>
) : StringPropertyProvider() {
    override val order: Float = 31.6f
    override val key: String = Parameters.OAID_MD5

    override fun provide(): String? = oaidManager.getOaid()?.let(converter::convert)
}