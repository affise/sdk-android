package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase


/**
 * Provider for parameter [Parameters.REFERRER_CLICK_TIME]
 *
 * @property useCase usecase to retrieve client-side timestamp, in seconds, when the referrer click happened.
 */
class ReferrerClickTimestampProvider(
    private val useCase: RetrieveInstallReferrerUseCase
) : LongPropertyProvider() {

    override val order: Float = 15.0f
    override val key: String = Parameters.REFERRER_CLICK_TIME

    override fun provide(): Long? = useCase.getInstallReferrer()
        ?.referrerClickTimestampSeconds
        ?.takeIf { it != 0L }
}
