package com.affise.attribution.modules.subscription

data class AffiseProductSubscriptionDetail(
    val offerToken: String,
    val offerId: String,
    val timeUnit: TimeUnitType?,
    val numberOfUnits: Long?,
)