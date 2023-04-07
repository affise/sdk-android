package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase

/**
 * Provider for parameter [Parameters.INSTALL_BEGIN_TIME]
 *
 * @property useCase usecase to retrieve install timestamp from
 */
class InstallBeginTimeProvider(
    private val useCase: RetrieveInstallReferrerUseCase
) : LongPropertyProvider() {

    override val order: Float = 11.0f
    override val key: String = Parameters.INSTALL_BEGIN_TIME

    override fun provide(): Long? = useCase.getInstallReferrer()
        ?.installBeginTimestampSeconds
        ?.takeIf { it != 0L }
}