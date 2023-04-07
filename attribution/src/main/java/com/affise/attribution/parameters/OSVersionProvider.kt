package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.OS_VERSION]
 *
 * @property buildConfigPropertiesProvider to retrieve release name
 */
class OSVersionProvider(
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 48.0f
    override val key: String = Parameters.OS_VERSION

    override fun provide(): String? = buildConfigPropertiesProvider.getReleaseName()
}