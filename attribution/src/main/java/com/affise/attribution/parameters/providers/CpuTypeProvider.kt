package com.affise.attribution.parameters.providers

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 *  Provider for parameter [ProviderType.CPU_TYPE]
 *
 * @property buildPropertiesProvider to retrieve supported ABIs
 */
class CpuTypeProvider(
    private val buildPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 22.0f
    override val key: ProviderType = ProviderType.CPU_TYPE

    override fun provide(): String = buildPropertiesProvider.getSupportedABIs().joinToString()
}