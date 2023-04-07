package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.Locale

/**
 *  Provider for parameter [Parameters.COUNTRY]
 */
class CountryProvider : StringPropertyProvider() {

    override val order: Float = 39.0f
    override val key: String = Parameters.COUNTRY

    override fun provide(): String? = Locale.getDefault().country
}