package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase

/**
 * Provider for parameter [ProviderType.AFFISE_DEVICE_ID]
 *
 * @property useCase to retrieve affise device id
 */
class AffiseDeviceIdProvider(
    private val useCase: FirstAppOpenUseCase
) : StringPropertyProvider() {

    override val order: Float = 27.0f
    override val key: ProviderType = ProviderType.AFFISE_DEVICE_ID

    override fun provide(): String? = useCase.getAffiseDeviseId()
}