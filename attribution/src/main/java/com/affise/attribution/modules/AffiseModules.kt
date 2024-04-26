package com.affise.attribution.modules


enum class AffiseModules(val module: String) {
    Advertising("com.affise.attribution.module.advertising.AdvertisingModule"),
    AndroidId("com.affise.attribution.module.androidid.AndroidIdModule"),
    Network("com.affise.attribution.module.network.NetworkModule"),
    Phone("com.affise.attribution.module.phone.PhoneModule"),
    Status("com.affise.attribution.module.status.StatusModule"),
    Subscription("com.affise.attribution.module.subscription.SubscriptionModule");

    companion object {
        @JvmStatic
        fun from(name: String?): AffiseModules? {
            name ?:  return null
            return entries.firstOrNull { it.module.contains(name, true) }
        }
    }
}

fun String.toAffiseModules(): AffiseModules? = AffiseModules.from(this)