package com.affise.attribution.module.subscription.store.utils

import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductType
import com.android.billingclient.api.ProductDetails

fun AffiseProduct.getProductDetails(): ProductDetails? = productDetails as? ProductDetails

internal fun AffiseProduct.setType(type: AffiseProductType?): AffiseProduct = when (type) {
    AffiseProductType.CONSUMABLE -> this.asConsumable()
    AffiseProductType.NON_CONSUMABLE -> this.asNonConsumable()
    else -> this
}
