package com.affise.attribution.parameters.providers

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.API_LEVEL_OS]
 *
 * @property buildConfigPropertiesProvider to retrieve sdk version
 */
class ApiLevelOSProvider(
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 46.0f
    override val key: ProviderType = ProviderType.API_LEVEL_OS

    override fun provide(): String = buildConfigPropertiesProvider
        .getSDKVersion()
        .toString()
}