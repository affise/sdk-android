package com.affise.attribution.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.oaid.OaidManager
import com.affise.attribution.parameters.base.StringPropertyProvider

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

    override fun provide(): String? = oaidManager.getOaid()?.let(converter::convert)
}