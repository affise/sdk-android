package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.BooleanPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase


/**
 * Provider for parameter [ProviderType.INSTALL_FIRST_EVENT]
 *
 * @property useCase to retrieve is first open from
 */
internal class InstallFirstEventProvider(
    private val useCase: FirstAppOpenUseCase
) : BooleanPropertyProvider() {

    override val order: Float = 10.0f
    override val key: ProviderType = ProviderType.INSTALL_FIRST_EVENT

    override fun provide(): Boolean? = useCase.isFirstOpen()
}