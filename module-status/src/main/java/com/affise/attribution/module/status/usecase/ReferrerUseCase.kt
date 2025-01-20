package com.affise.attribution.module.status.usecase

import com.affise.attribution.modules.AffiseKeyValue
import com.affise.attribution.referrer.OnReferrerCallback

internal interface ReferrerUseCase {

    fun getReferrer(onComplete: OnReferrerCallback)

    fun parseStatus(status: List<AffiseKeyValue>): String?
}