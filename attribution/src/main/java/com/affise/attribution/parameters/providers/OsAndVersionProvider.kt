package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.RemarketingUseCase

/**
 * Provider for parameter [ProviderType.OS_AND_VERSION]
 */
internal class OsAndVersionProvider(
    private val useCase: RemarketingUseCase
) : StringPropertyProvider() {

    override val order: Float = 68.0f
    override val key: ProviderType = ProviderType.OS_AND_VERSION

    override fun provide(): String = useCase.osAndVersion
}