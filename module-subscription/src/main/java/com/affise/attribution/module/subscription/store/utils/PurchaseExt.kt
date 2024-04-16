package com.affise.attribution.module.subscription.store.utils

import com.android.billingclient.api.Purchase

internal fun Purchase.getOriginalOrderId(): String? = this.orderId?.substringBefore("..")

// use one product for setProductDetailsParamsList to receive one purchase.productId in onPurchasesUpdated
internal fun Purchase.getFirstProductId(): String? = this.products.firstOrNull()