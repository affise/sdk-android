package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.RemarketingUseCase

/**
 * Provider for parameter [ProviderType.DEVICE]
 */
internal class DeviceProvider(
    private val useCase: RemarketingUseCase
) : StringPropertyProvider() {

    override val order: Float = 69.0f
    override val key: ProviderType = ProviderType.DEVICE

    override fun provide(): String = useCase.device
}