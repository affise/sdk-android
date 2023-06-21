package com.affise.attribution.modules


enum class AffiseModules(val module: String) {
    Advertising("com.affise.attribution.module.advertising.AdvertisingModule"),
    Network("com.affise.attribution.module.network.NetworkModule"),
    Phone("com.affise.attribution.module.phone.PhoneModule"),
    Status("com.affise.attribution.module.status.StatusModule");

    companion object {
        fun from(name: String?): AffiseModules? {
            return AffiseModules.values().firstOrNull { it.module == name }
        }
    }
}