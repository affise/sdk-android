package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase

/**
 * Provider for parameter [Parameters.AFFISE_ALT_DEVICE_ID]
 *
 * @property useCase to retrieve affise alt device id
 */
internal class AffiseAltDeviceIdProvider(
    private val useCase: FirstAppOpenUseCase
) : StringPropertyProvider() {
    override val order: Float = 28.0f
    override val key: String = Parameters.AFFISE_ALT_DEVICE_ID

    override fun provide(): String? = useCase.getAffiseAltDeviseId()
}