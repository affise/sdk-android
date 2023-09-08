package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.BooleanPropertyProvider
import com.affise.attribution.usecase.DeviceUseCase

/**
 * Provider for property [Parameters.IS_ROOTED]
 *
 * @property deviceUseCase to retrieve devise state
 */
internal class IsRootedProvider(
    private val deviceUseCase: DeviceUseCase
): BooleanPropertyProvider() {

    override val order: Float = 66.0f
    override val key: String = Parameters.IS_ROOTED

    override fun provide(): Boolean? = deviceUseCase.isRooted()
}