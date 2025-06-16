package com.affise.attribution.modules.subscription

import android.app.Activity
import com.affise.attribution.modules.AffiseModuleApi

interface AffiseSubscriptionApi : AffiseModuleApi {
    /**
     * Subscription Module fetch products for purchase from [productsIds]
     * (billingclient wrapper)
     */
    fun fetchProducts(
        productsIds: List<String>,
        callback: AffiseResultCallback<AffiseProductsResult>,
    )

    /**
     * Subscription Module purchase product
     * (billingclient wrapper)
     */
    fun purchase(
        activity: Activity,
        product: AffiseProduct,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    )

    /**
     * Subscription Module purchase product
     * (billingclient wrapper)
     */
    fun purchase(
        activity: Activity,
        productId: String,
        offerToken: String?,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    )
}