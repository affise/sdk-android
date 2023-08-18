package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains String value
 *
 * @property value the key of parameter
 */
enum class PredefinedString(private val value: String): Predefined {
    ADREV_AD_TYPE("adrev_ad_type" ),
    CITY("city" ),
    COUNTRY("country" ),
    REGION("region" ),
    CLASS("class" ),
    CONTENT_ID("content_id" ),
    CONTENT_TYPE("content_type" ),
    CURRENCY("currency" ),
    CUSTOMER_USER_ID("customer_user_id" ),
    DESCRIPTION("description" ),
    DESTINATION_A("destination_a" ),
    DESTINATION_B("destination_b" ),
    DESTINATION_LIST("destination_list" ),
    ORDER_ID("order_id" ),
    PAYMENT_INFO_AVAILABLE("payment_info_available" ),
    PREFERRED_NEIGHBORHOODS("preferred_neighborhoods" ),
    PURCHASE_CURRENCY("purchase_currency" ),
    RECEIPT_ID("receipt_id" ),
    REGISTRATION_METHOD("registration_method" ),
    SEARCH_STRING("search_string" ),
    SUBSCRIPTION_ID("subscription_id" ),
    SUCCESS("success" ),
    SUGGESTED_DESTINATIONS("suggested_destinations" ),
    SUGGESTED_HOTELS("suggested_hotels" ),
    VALIDATED("validated" ),
    ACHIEVEMENT_ID("achievement_id" ),
    COUPON_CODE("coupon_code" ),
    CUSTOMER_SEGMENT("customer_segment" ),
    DEEP_LINK("deep_link" ),
    NEW_VERSION("new_version" ),
    OLD_VERSION("old_version" ),
    PARAM_01("param_01" ),
    PARAM_02("param_02" ),
    PARAM_03("param_03" ),
    PARAM_04("param_04" ),
    PARAM_05("param_05" ),
    PARAM_06("param_06" ),
    PARAM_07("param_07" ),
    PARAM_08("param_08" ),
    PARAM_09("param_09" ),
    PARAM_10("param_10" ),
    REVIEW_TEXT("review_text" ),
    TUTORIAL_ID("tutorial_id" ),
    VIRTUAL_CURRENCY_NAME("virtual_currency_name" ),
    STATUS("status"),
    BRAND("brand"),
    BRICK("brick"),
    CATALOGUE_ID("catalogue_id"),
    CHANNEL_TYPE("channel_type"),
    CUSTOMER_TYPE("customer_type"),
    SEGMENT("segment"),
    UTM_CAMPAIGN("utm_campaign"),
    UTM_MEDIUM("utm_medium"),
    UTM_SOURCE("utm_source"),
    VERTICAL("vertical"),
    VOUCHER_CODE("voucher_code"),
    CLICK_ID("click_id"),
    CAMPAIGN_ID("campaign_id"),
    EVENT_NAME("event_name"),
    PID("pid"),
    PRODUCT_ID("product_id");

    override fun value(): String = "${Predefined.PREFIX}${this.value}"

    companion object {
        @JvmStatic
        fun from(name: String?): PredefinedString? = name?.let { value ->
            PredefinedString.values().firstOrNull { "${Predefined.PREFIX}${it.value}" == value }
        }
    }
}