package com.affise.attribution.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.parameters.base.StringWithParamPropertyProvider

/**
 * Provider for property [Parameters.AFFISE_APP_TOKEN]
 *
 * @property initProperties to retrieve appToken
 */
class AffAppTokenPropertyProvider(
    private val initProperties: InitPropertiesStorage,
    private val stringToSHA256Converter: Converter<String, String>
) : StringWithParamPropertyProvider() {

    override val order: Float = 61.0f
    override val key: String = Parameters.AFFISE_APP_TOKEN

    override fun provideWithParam(param: String): String = stringToSHA256Converter.convert(
        initProperties.getProperties()?.affiseAppId +
            param +
            initProperties.getProperties()?.secretId
    )
}