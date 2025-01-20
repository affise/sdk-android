package com.affise.attribution.module.status.usecase

import com.affise.attribution.modules.AffiseKeyValue
import com.affise.attribution.referrer.OnReferrerCallback

internal class ReferrerUseCaseImpl(
    private val checkStatusUseCase: CheckStatusUseCase?
) : ReferrerUseCase {

    private var referrer: String? = null
    private val keyIsOrganic: String = "is_organic"
    private val keyReferrer: String = "referrer"
    private val organic: String = "utm_source=apple-store&utm_medium=organic"

    private fun getStatusValue(key: String, status: List<AffiseKeyValue>): String? {
        return status.firstOrNull { it.key == key }?.value
    }

    override fun getReferrer(onComplete: OnReferrerCallback) {
        if (referrer != null) {
            onComplete.handleReferrer(referrer)
            return
        }

        if (checkStatusUseCase == null) {
            onComplete.handleReferrer(referrer)
            return
        }

        checkStatusUseCase.send {
            onComplete.handleReferrer(parseStatus(it))
        }
    }

    override fun parseStatus(status: List<AffiseKeyValue>): String? {
        if (referrer != null) return referrer


        val isOrganic = getStatusValue(keyIsOrganic, status)?.toBoolean() ?: false

        if (isOrganic) {
            referrer = organic
        } else {
            referrer = getStatusValue(keyReferrer, status)
        }
        return referrer
    }
}