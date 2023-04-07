package com.affise.attribution.parameters

import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for property [Parameters.AFFISE_PART_PARAM_NAME]
 *
 * @property initProperties to retrieve part param name
 */
class AffPartParamNamePropertyProvider(
    private val initProperties: InitPropertiesStorage
) : StringPropertyProvider() {

    override val order: Float = 59.0f
    override val key: String = Parameters.AFFISE_PART_PARAM_NAME

    override fun provide(): String? = initProperties.getProperties()?.partParamName
}

