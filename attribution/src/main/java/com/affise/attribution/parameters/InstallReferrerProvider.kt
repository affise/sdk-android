package com.affise.attribution.parameters

import android.app.Application
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.referrer.getPartnerKey
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase

/**
 * Provider for parameter [Parameters.REFERRER]
 *
 * @property app to get partner_key in assets
 * @property referrerUseCase usecase to retrieve install begin time
 */
class InstallReferrerProvider(
    private val app: Application,
    private val referrerUseCase: RetrieveInstallReferrerUseCase
) : StringPropertyProvider() {

    override val order: Float = 34.0f
    override val key: String = Parameters.REFERRER

    override fun provide(): String? {
        //Check referrer in partner_key
        val referrer = app.getPartnerKey()

        //if partner_key is empty or null use installReferrer
        return if (referrer.isNullOrEmpty()) {
            referrerUseCase.getInstallReferrer()?.installReferrer
        } else {
            referrer
        }
    }
}