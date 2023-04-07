package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase

/**
 * Provider for parameter [Parameters.RANDOM_USER_ID]
 *
 * @property useCase to retrieve random user id
 */
internal class RandomUserIdProvider(
    private val useCase: FirstAppOpenUseCase
) : StringPropertyProvider() {

    override val order: Float = 49.0f
    override val key: String = Parameters.RANDOM_USER_ID

    override fun provide(): String? = useCase.getRandomUserId()
}