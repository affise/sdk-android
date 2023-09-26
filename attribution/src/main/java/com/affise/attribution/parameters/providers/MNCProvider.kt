package com.affise.attribution.parameters.providers

import android.app.Application
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.MNCODE]
 *
 * @property app manager to fetch resources from
 */
class MNCProvider(
    private val app: Application
) : StringPropertyProvider() {

    override val order: Float = 37.0f
    override val key: ProviderType = ProviderType.MNCODE

    override fun provide(): String = app.resources.configuration.mnc.toString()
}