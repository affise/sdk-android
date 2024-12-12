package com.affise.attribution.modules


enum class AffiseModules(val module: String) {
    Advertising("com.affise.attribution.module.advertising.AdvertisingModule"),
    AndroidId("com.affise.attribution.module.androidid.AndroidIdModule"),
    Link("com.affise.attribution.module.link.LinkModule"),
    Network("com.affise.attribution.module.network.NetworkModule"),
    Phone("com.affise.attribution.module.phone.PhoneModule"),
    Status("com.affise.attribution.module.status.StatusModule"),
    Subscription("com.affise.attribution.module.subscription.SubscriptionModule"),
    RuStore("com.affise.attribution.module.rustore.RuStoreModule"),
    Huawei("com.affise.attribution.module.huawei.HuaweiModule");

    companion object {
        @JvmStatic
        fun from(name: String?): AffiseModules? {
            name ?:  return null
            return values().firstOrNull { it.module.contains(name, true) }
        }
    }
}

fun String.toAffiseModules(): AffiseModules? = AffiseModules.from(this)