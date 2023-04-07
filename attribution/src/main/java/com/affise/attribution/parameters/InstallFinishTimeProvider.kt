package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase

/**
 * Provider for parameter [Parameters.INSTALL_FINISH_TIME]
 *
 * @property useCase usecase to retrieve install timestamp from
 */
internal class InstallFinishTimeProvider(
    private val useCase: FirstAppOpenUseCase
) : LongPropertyProvider() {

    override val order: Float = 12.0f
    override val key: String = Parameters.INSTALL_FINISH_TIME

    override fun provide(): Long? = useCase.getFirstOpenDate()
        ?.time
        ?.takeIf { it != 0L }
}