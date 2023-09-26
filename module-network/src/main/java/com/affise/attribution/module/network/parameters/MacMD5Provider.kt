package com.affise.attribution.module.network.parameters


import android.annotation.SuppressLint
import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.MAC_MD5]
 *
 * @property macProvider to retrieve mac address provider
 * @property converter for convert mac address to md5
 */
class MacMD5Provider(
    private val macProvider: MacProvider,
    private val converter: Converter<@JvmSuppressWildcards String, @JvmSuppressWildcards String>
) : StringPropertyProvider() {
    override val order: Float = 31.2f
    override val key: ProviderType = ProviderType.MAC_MD5

    @SuppressLint("HardwareIds")
    override fun provide(): String? = macProvider.provide()?.let(converter::convert)
}