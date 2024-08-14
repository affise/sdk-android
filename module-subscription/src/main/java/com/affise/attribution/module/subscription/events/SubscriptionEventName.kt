package com.affise.attribution.module.subscription.events


internal enum class SubscriptionEventName(val eventName: String) {
    AF_PURCHASE("afPurchase"),
    AF_FAILED_PURCHASE("afFailedPurchase"),
    AF_SUBSCRIPTION("afSubscription"),
    AF_RENEWED_SUBSCRIPTION("afRenewedSubscription"),
    AF_FAILED_SUBSCRIPTION("afFailedSubscription"),
    ;

    companion object {
        @JvmStatic
        fun from(name: String?): SubscriptionEventName? = name?.let { value ->
            SubscriptionEventName.values().firstOrNull { it.eventName == value }
        }
    }
}