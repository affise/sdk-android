package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.BooleanPropertyProvider
import com.affise.attribution.usecase.DeviceUseCase

/**
 * Provider for property [Parameters.IS_EMULATOR]
 *
 * @property deviceUseCase to retrieve devise state
 */
internal class IsEmulatorProvider(
    private val deviceUseCase: DeviceUseCase
): BooleanPropertyProvider() {

    override val order: Float = 67.0f
    override val key: String = Parameters.IS_EMULATOR

    override fun provide(): Boolean? = deviceUseCase.isEmulator()
}