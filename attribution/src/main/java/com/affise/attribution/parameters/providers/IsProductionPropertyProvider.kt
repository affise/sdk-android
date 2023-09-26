package com.affise.attribution.parameters.providers

import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for property [ProviderType.AFFISE_SDK_POS]
 *
 * @property initProperties to retrieve is production
 */
class IsProductionPropertyProvider(
    private val initProperties: InitPropertiesStorage
) : StringPropertyProvider() {

    override val order: Float = 50.0f
    override val key: ProviderType = ProviderType.AFFISE_SDK_POS

    override fun provide(): String = if (initProperties.getProperties()?.isProduction == true) {
        TYPE_PRODUCTION
    } else {
        TYPE_SANDBOX
    }

    companion object {
        const val TYPE_SANDBOX = "Sandbox"
        const val TYPE_PRODUCTION = "Production"
    }
}