package com.affise.attribution.modules


enum class AffiseModules(val module: String) {
    Advertising("com.affise.attribution.module.advertising.AdvertisingModule"),
    Network("com.affise.attribution.module.network.NetworkModule"),
    Phone("com.affise.attribution.module.phone.PhoneModule"),
    Status("com.affise.attribution.module.status.StatusModule");

    companion object {
        @JvmStatic
        fun from(name: String?): AffiseModules? {
            name ?:  return null
            return AffiseModules.values().firstOrNull { it.module.contains(name, true) }
        }
    }
}

fun String.toAffiseModules(): AffiseModules? = AffiseModules.from(this)