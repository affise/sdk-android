package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.API_LEVEL_OS]
 *
 * @property buildConfigPropertiesProvider to retrieve sdk version
 */
class ApiLevelOSProvider(
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 46.0f
    override val key: String = Parameters.API_LEVEL_OS

    override fun provide(): String = buildConfigPropertiesProvider
        .getSDKVersion()
        .toString()
}