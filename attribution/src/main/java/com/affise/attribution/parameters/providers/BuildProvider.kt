package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.RemarketingUseCase

/**
 * Provider for parameter [ProviderType.BUILD]
 */
internal class BuildProvider(
    private val useCase: RemarketingUseCase
) : StringPropertyProvider() {

    override val order: Float = 70.0f
    override val key: ProviderType = ProviderType.BUILD

    override fun provide(): String = useCase.build
}