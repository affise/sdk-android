package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase


/**
 * Provider for parameter [ProviderType.REFERRER_CLICK_TIME_SERVER]
 *
 * @property useCase usecase to retrieve server-side timestamp, in seconds, when the referrer click happened.
 */
class ReferrerClickTimestampServerProvider(
    private val useCase: RetrieveInstallReferrerUseCase
) : LongPropertyProvider() {

    override val order: Float = 16.0f
    override val key: ProviderType = ProviderType.REFERRER_CLICK_TIME_SERVER

    override fun provide(): Long? = useCase.getInstallReferrer()
        ?.referrerClickTimestampServerSeconds
        ?.takeIf { it != 0L }
}

