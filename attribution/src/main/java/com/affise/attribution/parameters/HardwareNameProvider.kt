package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.HARDWARE_NAME]
 *
 * @property propertiesProvider provider for Build properties
 */
class HardwareNameProvider(
    private val propertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 23.0f
    override val key: String = Parameters.HARDWARE_NAME

    override fun provide(): String? = propertiesProvider.getHardware()
}