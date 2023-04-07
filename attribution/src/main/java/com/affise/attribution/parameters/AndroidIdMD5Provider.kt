package com.affise.attribution.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.ANDROID_ID_MD5]
 *
 * @property androidIdProvider to retrieve android id
 * @property strToMd5Converter to convert android id to md5
 */
class AndroidIdMD5Provider(
    private val androidIdProvider: StringPropertyProvider,
    private val strToMd5Converter: Converter<@JvmSuppressWildcards String, @JvmSuppressWildcards String>
) : StringPropertyProvider() {

    override val order: Float = 31.0f
    override val key: String = Parameters.ANDROID_ID_MD5

    override fun provide(): String? = androidIdProvider.provide()?.let(strToMd5Converter::convert)
}