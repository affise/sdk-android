package com.affise.attribution.module.subscription

import android.app.Activity
import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductType
import com.affise.attribution.modules.subscription.AffiseProductsResult
import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.affise.attribution.modules.subscription.AffiseResult
import com.affise.attribution.modules.subscription.AffiseResultCallback
import com.affise.attribution.modules.subscription.AffiseSubscriptionApi
import com.affise.attribution.modules.subscription.AffiseSubscriptionError


object AffiseSubscription : AffiseSubscriptionApi {

    override fun fetchProducts(
        productsIds: List<String>,
        callback: AffiseResultCallback<AffiseProductsResult>
    ) {
        SubscriptionModule.instance
            ?.fetchProducts(productsIds, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }

    override fun purchase(
        activity: Activity,
        product: AffiseProduct,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        SubscriptionModule.instance
            ?.purchase(activity, product, type, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }

    override fun purchase(
        activity: Activity,
        productId: String,
        offerToken: String?,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>
    ) {
        SubscriptionModule.instance
            ?.purchase(activity, productId, offerToken, type, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }
}