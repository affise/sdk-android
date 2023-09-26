package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.Locale

/**
 * Provider for parameter [ProviderType.LANGUAGE]
 */
class LanguageProvider : StringPropertyProvider() {

    override val order: Float = 40.0f
    override val key: ProviderType = ProviderType.LANGUAGE

    override fun provide(): String? = Locale.getDefault().toLanguageTag()
}