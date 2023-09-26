package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.BooleanPropertyProvider
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase


/**
 * Provider for parameter [ProviderType.REFERRER_GOOGLE_PLAY_INSTANT]
 *
 * @property referrerUseCase usecase to retrieve your app's instant experience was launched within the past 7 days.
 */
class ReferrerGooglePlayInstantProvider(
    private val referrerUseCase: RetrieveInstallReferrerUseCase
) : BooleanPropertyProvider() {

    override val order: Float = 17.0f
    override val key: ProviderType = ProviderType.REFERRER_GOOGLE_PLAY_INSTANT

    override fun provide(): Boolean? = referrerUseCase.getInstallReferrer()
        ?.googlePlayInstantParam
}
