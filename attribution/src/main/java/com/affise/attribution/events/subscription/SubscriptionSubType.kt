package com.affise.attribution.events.subscription

enum class SubscriptionSubType(val typeName: String) {
    AFFISE_SUB_INITIAL_SUBSCRIPTION("affise_sub_initial_subscription"),
    AFFISE_SUB_INITIAL_TRIAL("affise_sub_initial_trial"),
    AFFISE_SUB_INITIAL_OFFER("affise_sub_initial_offer"),

    AFFISE_SUB_FAILED_TRIAL("affise_sub_failed_trial"),
    AFFISE_SUB_FAILED_OFFERISE("affise_sub_failed_offer"),
    AFFISE_SUB_FAILED_SUBSCRIPTION("affise_sub_failed_subscription"),
    AFFISE_SUB_FAILED_TRIAL_FROM_RETRY("affise_sub_failed_trial_from_retry"),
    AFFISE_SUB_FAILED_OFFER_FROM_RETRY("affise_sub_failed_offer_from_retry"),
    AFFISE_SUB_FAILED_SUBSCRIPTION_FROM_RETRY("affise_sub_failed_subscription_from_retry"),

    AFFISE_SUB_TRIAL_IN_RETRY("affise_sub_trial_in_retry"),
    AFFISE_SUB_OFFER_IN_RETRY("affise_sub_offer_in_retry"),
    AFFISE_SUB_SUBSCRIPTION_IN_RETRY("affise_sub_subscription_in_retry"),

    AFFISE_SUB_CONVERTED_TRIAL("affise_sub_converted_trial"),
    AFFISE_SUB_CONVERTED_OFFER("affise_sub_converted_offer"),

    AFFISE_SUB_REACTIVATED_SUBSCRIPTION("affise_sub_reactivated_subscription"),

    AFFISE_SUB_RENEWED_SUBSCRIPTION("affise_sub_renewed_subscription"),

    AFFISE_SUB_CONVERTED_TRIAL_FROM_RETRY("affise_sub_converted_trial_from_retry"),
    AFFISE_SUB_CONVERTED_OFFER_FROM_RETRY("affise_sub_converted_offer_from_retry"),
    AFFISE_SUB_RENEWED_SUBSCRIPTION_FROM_RETRY("affise_sub_renewed_subscription_from_retry"),

    AFFISE_SUB_UNSUBSCRIPTION("affise_sub_unsubscription");

    companion object {
        @JvmStatic
        fun from(name: String?): SubscriptionSubType? = name?.let { value ->
            SubscriptionSubType.values().firstOrNull { it.typeName == value }
        }
    }
}