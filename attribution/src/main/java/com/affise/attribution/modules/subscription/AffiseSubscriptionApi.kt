package com.affise.attribution.modules.subscription

import android.app.Activity
import com.affise.attribution.modules.AffiseModuleApi

interface AffiseSubscriptionApi : AffiseModuleApi {
    fun fetchProducts(
        productsIds: List<String>,
        callback: AffiseResultCallback<AffiseProductsResult>,
    )

    fun purchase(
        activity: Activity,
        product: AffiseProduct,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    )

    fun purchase(
        activity: Activity,
        productId: String,
        offerToken: String?,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    )
}