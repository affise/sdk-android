package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.BooleanPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase


/**
 * Provider for parameter [Parameters.INSTALL_FIRST_EVENT]
 *
 * @property useCase to retrieve is first open from
 */
internal class InstallFirstEventProvider(
    private val useCase: FirstAppOpenUseCase
) : BooleanPropertyProvider() {

    override val order: Float = 10.0f
    override val key: String = Parameters.INSTALL_FIRST_EVENT

    override fun provide(): Boolean? = useCase.isFirstOpen()
}