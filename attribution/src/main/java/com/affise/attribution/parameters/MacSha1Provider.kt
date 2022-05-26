package com.affise.attribution.parameters

import android.annotation.SuppressLint
import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.MAC_SHA1]
 *
 * @property macProvider to retrieve mac address provider
 * @property stringToSHA1Converter for convert mac address to SHA1
 */
class MacSha1Provider(
    private val macProvider: MacProvider,
    private val stringToSHA1Converter: Converter<String, String>
) : StringPropertyProvider() {

    @SuppressLint("HardwareIds")
    override fun provide(): String? = macProvider.provide()?.let(stringToSHA1Converter::convert)
}