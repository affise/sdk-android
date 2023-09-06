package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.BooleanPropertyProvider
import com.affise.attribution.usecase.IsRootedUseCase

internal class IsRootedProvider(
    private val isRootedUseCase: IsRootedUseCase
): BooleanPropertyProvider() {

    override val order: Float = 66.0f
    override val key: String = Parameters.IS_ROOTED

    override fun provide(): Boolean? = isRootedUseCase.isRooted()
}