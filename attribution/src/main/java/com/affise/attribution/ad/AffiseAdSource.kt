package com.affise.attribution.ad


enum class AffiseAdSource(val type: String) {
    ADMOB("admob"),
    ADMOST("admost"),
    APPLOVIN_MAX("applovin_max"),
    HELIUM_CHARTBOOST("helium_chartboost"),
    IRONSOURCE("ironsource"),
    ;

    companion object {
        @JvmStatic
        fun from(source: String?): AffiseAdSource? = source?.let { value ->
            AffiseAdSource.values().firstOrNull { it.type == value }
        }
    }
}