package com.affise.attribution.modules


enum class AffiseModules(val module: String) {
    Advertising("Advertising"),
    AndroidId("AndroidId"),
    AppsFlyer("AppsFlyer"),
    Link("Link"),
    Network("Network"),
    Phone("Phone"),
    Status("Status"),
    Subscription("Subscription"),
    RuStore("RuStore"),
    Huawei("Huawei"),
    Meta("Meta");

    internal val className: String = "${AffiseModules.MODULE_PREFIX}.${this.module.lowercase()}.${this.module}Module"

    companion object {
        @JvmStatic
        fun from(name: String?): AffiseModules? {
            name ?:  return null
            return values().firstOrNull { it.module.contains(name, true) }
        }

        private const val MODULE_PREFIX = "com.affise.attribution.module"
    }
}

fun String.toAffiseModules(): AffiseModules? = AffiseModules.from(this)