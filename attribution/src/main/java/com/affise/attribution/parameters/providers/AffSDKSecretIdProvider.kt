package com.affise.attribution.parameters.providers

import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.AFFISE_SDK_SECRET_ID]
 *
 * @property initProperties to retrieve id from
 */
internal class AffSDKSecretIdProvider(
    private val initProperties: InitPropertiesStorage
) : StringPropertyProvider() {

    override val order: Float = 63.0f
    override val key: ProviderType = ProviderType.AFFISE_SDK_SECRET_ID

    override fun provide(): String? = initProperties.getProperties()?.secretKey
}