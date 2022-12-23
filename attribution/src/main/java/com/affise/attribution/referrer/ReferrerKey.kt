package com.affise.attribution.referrer

/**
 * Type of referrer affise key
 */
enum class ReferrerKey(val type: String) {
    AD_ID("ad_id"),
    CAMPAIGN_ID("campaign_id"),
    CLICK_ID("clickid"),
    AFFISE_AD("affise_ad"),
    AFFISE_AD_ID("affise_ad_id"),
    AFFISE_AD_TYPE("affise_ad_type"),
    AFFISE_ADSET("affise_adset"),
    AFFISE_ADSET_ID("affise_adset_id"),
    AFFISE_AFFC_ID("affise_affc_id"),
    AFFISE_CHANNEL("affise_channel"),
    AFFISE_CLICK_LOOK_BACK("affise_click_lookback"),
    AFFISE_COST_CURRENCY("affise_cost_currency"),
    AFFISE_COST_MODEL("affise_cost_model"),
    AFFISE_COST_VALUE("affise_cost_value"),
    AFFISE_DEEPLINK("affise_deeplink"),
    AFFISE_KEYWORDS("affise_keywords"),
    AFFISE_MEDIA_TYPE("affise_media_type"),
    AFFISE_MODEL("affise_model"),
    AFFISE_OS("affise_os"),
    AFFISE_PARTNER("affise_partner"),
    AFFISE_REF("affise_ref"),
    AFFISE_SITE_ID("affise_siteid"),
    AFFISE_SUB_SITE_ID("affise_sub_siteid"),
    AFFC("affc"),
    PID("pid"),
    SUB_1("sub1"),
    SUB_2("sub2"),
    SUB_3("sub3"),
    SUB_4("sub4"),
    SUB_5("sub5")
}