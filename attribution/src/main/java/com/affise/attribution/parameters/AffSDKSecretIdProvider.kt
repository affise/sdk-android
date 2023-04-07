package com.affise.attribution.parameters

import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.AFFISE_SDK_SECRET_ID]
 *
 * @property initProperties to retrieve id from
 */
internal class AffSDKSecretIdProvider(
    private val initProperties: InitPropertiesStorage
) : StringPropertyProvider() {

    override val order: Float = 63.0f
    override val key: String = Parameters.AFFISE_SDK_SECRET_ID

    override fun provide(): String? = initProperties.getProperties()?.secretId
}