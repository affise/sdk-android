package com.affise.attribution.module.subscription.store.event

import com.affise.attribution.events.Event
import com.affise.attribution.events.parameters.PredefinedFloat
import com.affise.attribution.events.parameters.PredefinedLong
import com.affise.attribution.events.parameters.PredefinedString
import com.affise.attribution.events.predefined.FailedPurchaseEvent
import com.affise.attribution.events.predefined.PurchaseEvent
import com.affise.attribution.events.predefined.SubscribeEvent
import com.affise.attribution.events.subscription.FailedSubscriptionEvent
import com.affise.attribution.events.subscription.RenewedSubscriptionEvent
import com.affise.attribution.module.subscription.store.utils.getOriginalOrderId
import com.affise.attribution.module.subscription.store.utils.isOk
import com.affise.attribution.module.subscription.store.utils.responseStatus
import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductType
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase

internal object OperationEvent {
    fun create(
        purchase: Purchase?,
        product: AffiseProduct,
        billingResult: BillingResult,
        failed: Boolean = false
    ): Event {
        val originalOrderId = purchase?.getOriginalOrderId()

        val event = when (product.productType) {
            AffiseProductType.CONSUMABLE, AffiseProductType.NON_CONSUMABLE -> {
                if (failed) {
                    FailedPurchaseEvent()
                } else {
                    PurchaseEvent()
                }
                    .addPredefinedParameter(PredefinedString.PRODUCT_ID, product.productId)
                    .addPredefinedParameter(
                        PredefinedString.PRODUCT_TYPE,
                        product.productType.value
                    )
            }

            AffiseProductType.RENEWABLE_SUBSCRIPTION, AffiseProductType.NON_RENEWABLE_SUBSCRIPTION -> {
                val productType = when(purchase?.isAutoRenewing) {
                    true -> AffiseProductType.RENEWABLE_SUBSCRIPTION
                    false -> AffiseProductType.NON_RENEWABLE_SUBSCRIPTION
                    else -> product.productType
                }

                if (failed) {
                    FailedSubscriptionEvent()
                } else {
                    if (originalOrderId.isNullOrEmpty()) {
                        SubscribeEvent()
                    } else {
                        RenewedSubscriptionEvent()
                    }
                }
                    .addPredefinedParameter(
                        PredefinedString.SUBSCRIPTION_ID,
                        product.productId
                    )
                    .addPredefinedParameter(
                        PredefinedString.SUBSCRIPTION_TYPE,
                        productType.value
                    )
                    .addPredefinedParameter(
                        PredefinedString.UNIT,
                        product.subscription?.timeUnit?.value ?: ""
                    )
                    .addPredefinedParameter(
                        PredefinedLong.QUANTITY,
                        product.subscription?.numberOfUnits ?: 0L
                    )
            }
        }

        return event
            .addPredefinedParameter(
                PredefinedString.ORDER_ID,
                purchase?.orderId ?: ""
            )
            .addPredefinedParameter(
                PredefinedString.ORIGINAL_ORDER_ID,
                originalOrderId ?: ""
            )
            .addPredefinedParameter(
                PredefinedString.CURRENCY,
                product.price?.currencyCode ?: ""
            )
            .addPredefinedParameter(
                PredefinedFloat.PRICE,
                product.price?.value ?: 0.0f
            )
            .apply {
                if (!billingResult.isOk()) {
                    addPredefinedParameter(
                        PredefinedString.STATUS,
                        billingResult.responseStatus()
                    )
                }
            }
    }
}