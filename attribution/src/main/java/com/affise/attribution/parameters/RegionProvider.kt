package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.Locale

/**
 * Provider for parameter [Parameters.REGION]
 */
class RegionProvider : StringPropertyProvider() {

    override fun provide(): String? = Locale.getDefault().country
}