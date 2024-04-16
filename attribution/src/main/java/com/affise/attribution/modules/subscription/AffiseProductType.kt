package com.affise.attribution.modules.subscription

enum class AffiseProductType(val value: String) {
    CONSUMABLE("consumable"),
    NON_CONSUMABLE("non_consumable"),
    RENEWABLE_SUBSCRIPTION("renewable_subscription"),
    NON_RENEWABLE_SUBSCRIPTION("non_renewable_subscription");

    companion object {
        @JvmStatic
        fun from(name: String?): AffiseProductType? = name?.let { value ->
            entries.firstOrNull { it.value == value }
        }
    }
}