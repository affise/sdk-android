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

    override fun provide(): Boolean? = useCase.isFirstOpen()
}