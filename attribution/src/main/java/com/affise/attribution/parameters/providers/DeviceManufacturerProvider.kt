package com.affise.attribution.parameters.providers

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 *  Provider for parameter [ProviderType.DEVICE_MANUFACTURER]
 *
 * @property propertiesProvider provider for Build properties
 */
class DeviceManufacturerProvider(
    private val propertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 24.0f
    override val key: ProviderType = ProviderType.DEVICE_MANUFACTURER

    override fun provide(): String? = propertiesProvider.getManufacturer()
}