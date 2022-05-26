package com.affise.attribution.parameters

import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.AFFISE_APP_ID]
 *
 * @property storage to retrieve affise app id
 */
class AffiseAppIdProvider(
    private val storage: InitPropertiesStorage
) : StringPropertyProvider() {

    override fun provide(): String? = storage.getProperties()?.affiseAppId
}