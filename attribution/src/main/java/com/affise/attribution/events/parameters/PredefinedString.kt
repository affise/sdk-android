package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains String value
 *
 * @property value the key of parameter
 */
enum class PredefinedString(private val value: String): Predefined {
    ADREV_AD_TYPE("affise_p_adrev_ad_type" ),
    CITY("affise_p_city" ),
    COUNTRY("affise_p_country" ),
    REGION("affise_p_region" ),
    CLASS("affise_p_class" ),
    CONTENT_ID("affise_p_content_id" ),
    CONTENT_TYPE("affise_p_content_type" ),
    CURRENCY("affise_p_currency" ),
    CUSTOMER_USER_ID("affise_p_customer_user_id" ),
    DESCRIPTION("affise_p_description" ),
    DESTINATION_A("affise_p_destination_a" ),
    DESTINATION_B("affise_p_destination_b" ),
    DESTINATION_LIST("affise_p_destination_list" ),
    ORDER_ID("affise_p_order_id" ),
    PAYMENT_INFO_AVAILABLE("affise_p_payment_info_available" ),
    PREFERRED_NEIGHBORHOODS("affise_p_preferred_neighborhoods" ),
    PURCHASE_CURRENCY("affise_p_purchase_currency" ),
    RECEIPT_ID("affise_p_receipt_id" ),
    REGISTRATION_METHOD("affise_p_registration_method" ),
    SEARCH_STRING("affise_p_search_string" ),
    SUBSCRIPTION_ID("affise_p_subscription_id" ),
    SUCCESS("affise_p_success" ),
    SUGGESTED_DESTINATIONS("affise_p_suggested_destinations" ),
    SUGGESTED_HOTELS("affise_p_suggested_hotels" ),
    VALIDATED("affise_p_validated" ),
    ACHIEVEMENT_ID("affise_p_achievement_id" ),
    COUPON_CODE("affise_p_coupon_code" ),
    CUSTOMER_SEGMENT("affise_p_customer_segment" ),
    DEEP_LINK("affise_p_deep_link" ),
    NEW_VERSION("affise_p_new_version" ),
    OLD_VERSION("affise_p_old_version" ),
    PARAM_01("affise_p_param_01" ),
    PARAM_02("affise_p_param_02" ),
    PARAM_03("affise_p_param_03" ),
    PARAM_04("affise_p_param_04" ),
    PARAM_05("affise_p_param_05" ),
    PARAM_06("affise_p_param_06" ),
    PARAM_07("affise_p_param_07" ),
    PARAM_08("affise_p_param_08" ),
    PARAM_09("affise_p_param_09" ),
    PARAM_10("affise_p_param_10" ),
    REVIEW_TEXT("affise_p_review_text" ),
    TUTORIAL_ID("affise_p_tutorial_id" ),
    VIRTUAL_CURRENCY_NAME("affise_p_virtual_currency_name" ),
    STATUS("status");

    override fun value(): String = this.value
}