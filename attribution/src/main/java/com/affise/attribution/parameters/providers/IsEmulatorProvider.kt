package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.BooleanPropertyProvider
import com.affise.attribution.usecase.DeviceUseCase

/**
 * Provider for property [ProviderType.IS_EMULATOR]
 *
 * @property deviceUseCase to retrieve devise state
 */
internal class IsEmulatorProvider(
    private val deviceUseCase: DeviceUseCase
): BooleanPropertyProvider() {

    override val order: Float = 67.0f
    override val key: ProviderType = ProviderType.IS_EMULATOR

    override fun provide(): Boolean? = deviceUseCase.isEmulator()
}