package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase

/**
 * Provider for parameter [ProviderType.REFERRER_INSTALL_VERSION]
 *
 * @property useCase usecase to retrieve install version from
 */
class ReferrerInstallVersionProvider(
    private val useCase: RetrieveInstallReferrerUseCase
) : StringPropertyProvider() {

    override val order: Float = 13.0f
    override val key: ProviderType = ProviderType.REFERRER_INSTALL_VERSION

    override fun provide(): String? = useCase.getInstallReferrer()?.installVersion
}