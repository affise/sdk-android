package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.StoreInstallReferrerUseCase

/**
 * Provider for parameter [ProviderType.REFERRAL_TIME]
 *
 * @property referrerUseCase usecase to retrieve install begin time
 */
class ReferralTimeProvider(
    private val referrerUseCase: StoreInstallReferrerUseCase,
) : LongPropertyProvider() {

    override val order: Float = 14.0f
    override val key: ProviderType = ProviderType.REFERRAL_TIME

    override fun provide(): Long? = referrerUseCase.getInstallReferrerData()
        ?.installBeginTimestampServerSeconds
}