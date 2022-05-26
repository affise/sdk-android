package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase

/**
 * Provider for parameter [Parameters.REFERRAL_TIME]
 *
 * @property referrerUseCase usecase to retrieve install begin time
 */
class ReferralTimeProvider(
    private val referrerUseCase: RetrieveInstallReferrerUseCase
) : LongPropertyProvider() {

    override fun provide(): Long? = referrerUseCase.getInstallReferrer()
        ?.installBeginTimestampServerSeconds
}