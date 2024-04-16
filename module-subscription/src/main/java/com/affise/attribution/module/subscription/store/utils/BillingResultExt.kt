package com.affise.attribution.module.subscription.store.utils

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult

internal fun BillingResult.responseStatus(): String = when (this.responseCode) {
    BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED -> "FEATURE_NOT_SUPPORTED"
    BillingClient.BillingResponseCode.SERVICE_DISCONNECTED -> "SERVICE_DISCONNECTED"
    BillingClient.BillingResponseCode.OK -> "OK"
    BillingClient.BillingResponseCode.USER_CANCELED -> "USER_CANCELED"
    BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE -> "SERVICE_UNAVAILABLE"
    BillingClient.BillingResponseCode.BILLING_UNAVAILABLE -> "BILLING_UNAVAILABLE"
    BillingClient.BillingResponseCode.ITEM_UNAVAILABLE -> "ITEM_UNAVAILABLE"
    BillingClient.BillingResponseCode.DEVELOPER_ERROR -> "DEVELOPER_ERROR"
    BillingClient.BillingResponseCode.ERROR -> "ERROR"
    BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> "ITEM_ALREADY_OWNED"
    BillingClient.BillingResponseCode.ITEM_NOT_OWNED -> "ITEM_NOT_OWNED"
    BillingClient.BillingResponseCode.NETWORK_ERROR -> "NETWORK_ERROR"
    else -> "${this.responseCode}"
}
