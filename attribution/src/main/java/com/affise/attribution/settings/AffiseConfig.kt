package com.affise.attribution.settings


enum class AffiseConfig(val key: String) {
    FbAppId("fb_app_id");

    companion object {
        @JvmStatic
        fun from(name: String?): AffiseConfig? {
            name ?:  return null
            return AffiseConfig.values().firstOrNull { it.key == name }
        }
    }
}