package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.RemarketingUseCase

/**
 * Provider for parameter [ProviderType.LANGUAGE]
 */
internal class LanguageProvider(
    private val useCase: RemarketingUseCase
) : StringPropertyProvider() {

    override val order: Float = 40.0f
    override val key: ProviderType = ProviderType.LANGUAGE

    override fun provide(): String = useCase.locale()
}