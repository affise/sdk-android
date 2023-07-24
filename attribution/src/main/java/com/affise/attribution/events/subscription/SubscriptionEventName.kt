package com.affise.attribution.events.subscription


enum class SubscriptionEventName(val eventName: String) {
    AFFISE_SUBSCRIPTION_ACTIVATION("affise_subscription_activation"),
    AFFISE_SUBSCRIPTION_CANCELLATION("affise_subscription_cancellation"),

    AFFISE_SUBSCRIPTION_ENTERED_BILLING_RETRY("affise_subscription_entered_billing_retry"),

    AFFISE_SUBSCRIPTION_FIRST_CONVERSION("affise_subscription_first_conversion"),

    AFFISE_SUBSCRIPTION_REACTIVATION("affise_subscription_reactivation"),

    AFFISE_SUBSCRIPTION_RENEWAL("affise_subscription_renewal"),

    AFFISE_SUBSCRIPTION_RENEWAL_FROM_BILLING_RETRY("affise_subscription_renewal_from_billing_retry"),

    AFFISE_UNSUBSCRIPTION("affise_unsubscription");

    companion object {
        @JvmStatic
        fun from(name: String?): SubscriptionEventName? = name?.let { value ->
            SubscriptionEventName.values().firstOrNull { it.eventName == value }
        }
    }
}