package com.affise.attribution.modules.subscription

data class AffisePurchasedInfo(
    val product: AffiseProduct?,
    val orderId: String?,
    val originalOrderId: String?,
    val purchase: Any?,
)