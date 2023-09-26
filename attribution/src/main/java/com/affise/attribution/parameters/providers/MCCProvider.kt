package com.affise.attribution.parameters.providers

import android.app.Application
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.MCCODE]
 *
 * @property app manager to fetch resources from
 */
class MCCProvider(
    private val app: Application
) : StringPropertyProvider() {

    override val order: Float = 36.0f
    override val key: ProviderType = ProviderType.MCCODE

    override fun provide(): String = app.resources.configuration.mcc.toString()
}