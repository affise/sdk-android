package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase

/**
 * Provider for parameter [Parameters.REFERRER_INSTALL_VERSION]
 *
 * @property useCase usecase to retrieve install version from
 */
class ReferrerInstallVersionProvider(
    private val useCase: RetrieveInstallReferrerUseCase
) : StringPropertyProvider() {

    override val order: Float = 13.0f
    override val key: String = Parameters.REFERRER_INSTALL_VERSION

    override fun provide(): String? = useCase.getInstallReferrer()?.installVersion
}