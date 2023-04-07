package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.Locale

/**
 * Provider for parameter [Parameters.LANGUAGE]
 */
class LanguageProvider : StringPropertyProvider() {

    override val order: Float = 40.0f
    override val key: String = Parameters.LANGUAGE

    override fun provide(): String? = Locale.getDefault().toLanguageTag()
}