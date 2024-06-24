package com.affise.attribution.internal.utils

import com.affise.attribution.modules.subscription.AffiseProduct

fun List<AffiseProduct>.toListOfMap(): List<Map<String, Any?>> {
    return this.map { it.toMap() }
}


fun AffiseProduct.toMap(): Map<String, Any?> {
    return mapOf(
        "productId" to this.productId,
        "title" to this.title,
        "description" to this.description,
        "productType" to this.productType.value,
        "price" to this.price?.let {
            mapOf(
                "value" to it.value,
                "currencyCode" to it.currencyCode,
                "formattedPrice" to it.formattedPrice,
            )
        },
        "subscription" to this.subscription?.let {
            mapOf(
                "offerToken" to it.offerToken,
                "offerId" to it.offerId,
                "timeUnit" to it.timeUnit?.value,
                "numberOfUnits" to it.numberOfUnits,
            )
        },
    )
}