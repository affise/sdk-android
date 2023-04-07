package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase

/**
 * Provider for parameter [Parameters.FIRST_OPEN_TIME]
 *
 * @property useCase to retrieve first open time from
 */
internal class FirstOpenTimeProvider(
    private val useCase: FirstAppOpenUseCase
) : LongPropertyProvider() {

    override val order: Float = 7.0f
    override val key: String = Parameters.FIRST_OPEN_TIME

    override fun provide(): Long? = useCase.getFirstOpenDate()?.time
}