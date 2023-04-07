package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase

/**
 * Provider for parameter [Parameters.AFFISE_DEVICE_ID]
 *
 * @property useCase to retrieve affise device id
 */
internal class AffiseDeviceIdProvider(
    private val useCase: FirstAppOpenUseCase
) : StringPropertyProvider() {

    override val order: Float = 27.0f
    override val key: String = Parameters.AFFISE_DEVICE_ID

    override fun provide(): String? = useCase.getAffiseDeviseId()
}