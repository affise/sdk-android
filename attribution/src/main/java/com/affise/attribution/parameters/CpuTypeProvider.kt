package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 *  Provider for parameter [Parameters.CPU_TYPE]
 *
 * @property buildPropertiesProvider to retrieve supported ABIs
 */
class CpuTypeProvider(
    private val buildPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 22.0f
    override val key: String = Parameters.CPU_TYPE

    override fun provide(): String = buildPropertiesProvider.getSupportedABIs().joinToString()
}