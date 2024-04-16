package com.affise.attribution.module.subscription.store.utils

import com.affise.attribution.modules.subscription.AffiseSubscriptionError
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeResult
import com.android.billingclient.api.ProductDetailsResult
import com.android.billingclient.api.PurchaseHistoryResult

internal fun BillingResult.isOk(): Boolean =
    this.responseCode == BillingClient.BillingResponseCode.OK

internal fun ProductDetailsResult.isOk(): Boolean = this.billingResult.isOk()

internal fun PurchaseHistoryResult.isOk(): Boolean = this.billingResult.isOk()

internal fun ConsumeResult.isOk(): Boolean = this.billingResult.isOk()

internal fun BillingResult.affiseError(): Exception? = when (this.responseCode) {
    BillingClient.BillingResponseCode.SERVICE_DISCONNECTED -> AffiseSubscriptionError.ConnectionError(this)
    BillingClient.BillingResponseCode.USER_CANCELED -> AffiseSubscriptionError.UserCanceled(this)
    BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE -> AffiseSubscriptionError.ConnectionError(this)
    BillingClient.BillingResponseCode.BILLING_UNAVAILABLE -> AffiseSubscriptionError.BillingUnavailable(this)
    BillingClient.BillingResponseCode.ITEM_UNAVAILABLE -> AffiseSubscriptionError.ItemUnavailable(this)
    BillingClient.BillingResponseCode.DEVELOPER_ERROR -> Exception(this.toString())
    BillingClient.BillingResponseCode.ERROR -> Exception(this.toString())
    BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> AffiseSubscriptionError.ItemAlreadyOwned(this)
    BillingClient.BillingResponseCode.ITEM_NOT_OWNED -> AffiseSubscriptionError.ItemNotOwned(this)
    BillingClient.BillingResponseCode.NETWORK_ERROR -> AffiseSubscriptionError.ConnectionError(this)
    else -> null
}
