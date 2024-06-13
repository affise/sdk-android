package com.affise.attribution.modules.subscription

import android.app.Activity
import com.affise.attribution.Affise
import com.affise.attribution.modules.AffiseModules


internal object AffiseSubscription : AffiseSubscriptionApi {

    internal val module: AffiseSubscriptionApi?
        get() {
            return _module ?: getSubscriptionModule().also {
                _module = it
            }
        }

    private var _module: AffiseSubscriptionApi? = null

    private fun getSubscriptionModule() = Affise.getApi()
        ?.moduleManager
        ?.getModule(AffiseModules.Subscription) as? AffiseSubscriptionApi

    override fun fetchProducts(
        productsIds: List<String>,
        callback: AffiseResultCallback<AffiseProductsResult>,
    ) {
        module
            ?.fetchProducts(productsIds, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }

    override fun purchase(
        activity: Activity,
        product: AffiseProduct,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        module
            ?.purchase(activity, product, type, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }

    override fun purchase(
        activity: Activity,
        productId: String,
        offerToken: String?,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        module
            ?.purchase(activity, productId, offerToken, type, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }
}
fun Affise.hasSubscriptionModule(): Boolean {
    return AffiseSubscription.module != null
}

fun Affise.fetchProducts(
    productsIds: List<String>, callback: AffiseResultCallback<AffiseProductsResult>,
) {
    AffiseSubscription.fetchProducts(productsIds, callback)
}

fun Affise.purchase(
    activity: Activity,
    product: AffiseProduct,
    type: AffiseProductType? = null,
    callback: AffiseResultCallback<AffisePurchasedInfo>,
) {
    AffiseSubscription.purchase(activity, product, type, callback)
}

fun Affise.purchase(
    activity: Activity,
    productId: String,
    offerToken: String? = null,
    type: AffiseProductType? = null,
    callback: AffiseResultCallback<AffisePurchasedInfo>,
) {
    AffiseSubscription.purchase(activity, productId, offerToken, type, callback)
}