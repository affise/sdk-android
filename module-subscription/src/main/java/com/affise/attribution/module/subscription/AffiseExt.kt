package com.affise.attribution.module.subscription

import android.app.Activity
import com.affise.attribution.Affise
import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductType
import com.affise.attribution.modules.subscription.AffiseProductsResult
import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.affise.attribution.modules.subscription.AffiseResultCallback


fun Affise.fetchProducts(
    productsIds: List<String>, callback: AffiseResultCallback<AffiseProductsResult>
) {
    AffiseSubscription.fetchProducts(productsIds, callback)
}

fun Affise.purchase(
    activity: Activity,
    product: AffiseProduct,
    type: AffiseProductType? = null,
    callback: AffiseResultCallback<AffisePurchasedInfo>
) {
    AffiseSubscription.purchase(activity, product, type, callback)
}

fun Affise.purchase(
    activity: Activity,
    productId: String,
    offerToken: String? = null,
    type: AffiseProductType? = null,
    callback: AffiseResultCallback<AffisePurchasedInfo>
) {
    AffiseSubscription.purchase(activity, productId, offerToken, type, callback)
}