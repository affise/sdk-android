package com.affise.attribution.module.subscription.store.utils

import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductPrice
import com.affise.attribution.modules.subscription.AffiseProductSubscriptionDetail
import com.affise.attribution.modules.subscription.AffiseProductType
import com.affise.attribution.modules.subscription.TimeUnitType
import com.android.billingclient.api.ProductDetails

internal fun ProductDetails.toAffiseProduct(): List<AffiseProduct> {
    val result: MutableList<AffiseProduct> = mutableListOf()

    this.oneTimePurchaseOfferDetails?.let {
        AffiseProduct(
            productId = this.productId,
            title = this.name,
            description = this.description,
            productType = AffiseProductType.CONSUMABLE,
            price = this.affisePrice(),
            subscription = null,
            productDetails = this,
        ).also {
            result.add(it)
        }
    }

    this.subscriptionOfferDetails?.forEach { offer ->
        offer.pricingPhases.pricingPhaseList.forEach { pricingPhase ->
            AffiseProduct(
                productId = this.productId,
                title = this.name,
                description = this.description,
                productType = pricingPhase.affiseProductType(),
                price = pricingPhase.affiseProductPrice(),
                subscription = offer.toAffiseProductSubscriptionDetail(pricingPhase),
                productDetails = this,
            ).also {
                result.add(it)
            }
        }
    }

    return result
}

internal fun ProductDetails.affisePrice(): AffiseProductPrice? {
    return this.oneTimePurchaseOfferDetails?.let {
        AffiseProductPrice(
            value = it.priceAmountMicros.div(1_000_000f),
            currencyCode = it.priceCurrencyCode,
            formattedPrice = it.formattedPrice
        )
    }
}

internal fun ProductDetails.PricingPhase.affiseProductPrice(): AffiseProductPrice =
    AffiseProductPrice(
        value = this.priceAmountMicros.div(1_000_000f),
        currencyCode = this.priceCurrencyCode,
        formattedPrice = this.formattedPrice
    )

internal fun ProductDetails.PricingPhase.affiseProductType(): AffiseProductType =
    when (this.recurrenceMode) {
        ProductDetails.RecurrenceMode.INFINITE_RECURRING -> AffiseProductType.RENEWABLE_SUBSCRIPTION
        ProductDetails.RecurrenceMode.FINITE_RECURRING -> AffiseProductType.RENEWABLE_SUBSCRIPTION
        ProductDetails.RecurrenceMode.NON_RECURRING -> AffiseProductType.NON_RENEWABLE_SUBSCRIPTION
        else -> AffiseProductType.NON_RENEWABLE_SUBSCRIPTION
    }

internal fun ProductDetails.PricingPhase.affisePeriod(): Pair<TimeUnitType, Long>? {
    if (!this.billingPeriod.startsWith("P", true)) return null

    val value = this.billingPeriod.substring(1, this.billingPeriod.length - 1)
    val duration = value.toLongOrNull() ?: return null

    val unitType = when (this.billingPeriod.last().uppercaseChar()) {
        'D' -> TimeUnitType.DAY
        'W' -> TimeUnitType.WEEK
        'M' -> TimeUnitType.MONTH
        'Y' -> TimeUnitType.YEAR
        else -> null
    } ?: return null

    return Pair(unitType, duration)
}

internal fun ProductDetails.SubscriptionOfferDetails.toAffiseProductSubscriptionDetail(
    pricingPhase: ProductDetails.PricingPhase,
): AffiseProductSubscriptionDetail {
    val period = pricingPhase.affisePeriod()
    return AffiseProductSubscriptionDetail(
        offerToken = this.offerToken,
        offerId = this.basePlanId,
        timeUnit = period?.first,
        numberOfUnits = period?.second,
    )
}
