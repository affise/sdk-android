package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.Locale

/**
 * Provider for parameter [Parameters.REGION]
 */
class RegionProvider : StringPropertyProvider() {

    override val order: Float = 38.0f
    override val key: String = Parameters.REGION

    override fun provide(): String? = Locale.getDefault().country
}