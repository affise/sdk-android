package com.affise.attribution.parameters

import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for property [Parameters.AFFISE_PART_PARAM_NAME_TOKEN]
 *
 * @property initProperties to retrieve part param name token
 */
class AffPartParamNameTokenPropertyProvider(
    private val initProperties: InitPropertiesStorage
) : StringPropertyProvider() {

    override val order: Float = 60.0f
    override val key: String = Parameters.AFFISE_PART_PARAM_NAME_TOKEN

    override fun provide(): String? = initProperties.getProperties()?.partParamNameToken
}