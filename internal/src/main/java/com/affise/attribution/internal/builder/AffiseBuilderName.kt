package com.affise.attribution.internal.builder

enum class AffiseBuilderName(val method: String) {
    AD_REVENUE("ad_revenue");

    companion object {
        @JvmStatic
        fun from(name: String?): AffiseBuilderName? = name?.let { method ->
            AffiseBuilderName.values().firstOrNull { it.method == method }
        }
    }
}