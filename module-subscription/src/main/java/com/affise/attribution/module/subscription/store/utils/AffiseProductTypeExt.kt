package com.affise.attribution.module.subscription.store.utils

import com.affise.attribution.modules.subscription.AffiseProductType
import com.android.billingclient.api.BillingClient

internal fun AffiseProductType.toProductType(): String = when (this) {
    AffiseProductType.CONSUMABLE -> BillingClient.ProductType.INAPP
    AffiseProductType.NON_CONSUMABLE -> BillingClient.ProductType.INAPP
    AffiseProductType.RENEWABLE_SUBSCRIPTION -> BillingClient.ProductType.SUBS
    AffiseProductType.NON_RENEWABLE_SUBSCRIPTION -> BillingClient.ProductType.SUBS
}
