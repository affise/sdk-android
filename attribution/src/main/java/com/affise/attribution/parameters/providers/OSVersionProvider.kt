package com.affise.attribution.parameters.providers

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.OS_VERSION]
 *
 * @property buildConfigPropertiesProvider to retrieve release name
 */
class OSVersionProvider(
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 48.0f
    override val key: ProviderType = ProviderType.OS_VERSION

    override fun provide(): String? = buildConfigPropertiesProvider.getReleaseName()
}