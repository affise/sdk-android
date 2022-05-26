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

    override fun provide(): String? = initProperties.getProperties()?.secretId
}