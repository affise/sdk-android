package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 *  Provider for parameter [Parameters.DEVICE_MANUFACTURER]
 *
 * @property propertiesProvider provider for Build properties
 */
class DeviceManufacturerProvider(
    private val propertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 24.0f
    override val key: String = Parameters.DEVICE_MANUFACTURER

    override fun provide(): String? = propertiesProvider.getManufacturer()
}