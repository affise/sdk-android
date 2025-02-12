package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.StoreInstallReferrerUseCase

/**
 * Provider for parameter [ProviderType.INSTALL_BEGIN_TIME]
 *
 * @property useCase usecase to retrieve install timestamp from
 */
class InstallBeginTimeProvider(
    private val useCase: StoreInstallReferrerUseCase,
) : LongPropertyProvider() {

    override val order: Float = 11.0f
    override val key: ProviderType = ProviderType.INSTALL_BEGIN_TIME

    override fun provide(): Long? = useCase.getInstallReferrerData()
        ?.installBeginTimestampSeconds
        ?.takeIf { it != 0L }
}