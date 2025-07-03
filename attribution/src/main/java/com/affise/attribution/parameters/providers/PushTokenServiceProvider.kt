package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.PushTokenUseCase

/**
 * Provider for parameter [ProviderType.PUSHTOKEN]
 *
 * @property useCase to retrieve push token service
 */
class PushTokenServiceProvider(
    private val useCase: PushTokenUseCase
) : StringPropertyProvider() {

    override val order: Float = 65.1f
    override val key: ProviderType = ProviderType.PUSHTOKEN_SERVICE

    override fun provide(): String? = useCase.getPushTokenService()
}