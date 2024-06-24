package com.affise.attribution.modules.subscription

import android.app.Activity
import com.affise.attribution.Affise
import com.affise.attribution.modules.AffiseModules


object AffiseSubscription {

    internal val api: AffiseSubscriptionApi?
        get() = module ?: Affise.Module.api<AffiseSubscriptionApi>(AffiseModules.Subscription).also {
            module = it
        }

    private var module: AffiseSubscriptionApi? = null

    /**
     * Get Subscription Module status
     * (billingclient wrapper)
     */
    @JvmStatic
    fun hasSubscriptionModule(): Boolean = api != null

    /**
     * Subscription Module fetch products for purchase from [productsIds]
     * (billingclient wrapper)
     */
    @JvmStatic
    fun fetchProducts(
        productsIds: List<String>,
        callback: AffiseResultCallback<AffiseProductsResult>,
    ) {
        api
            ?.fetchProducts(productsIds, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }

    /**
     * Subscription Module purchase product
     * (billingclient wrapper)
     */
    @JvmStatic
    fun purchase(
        activity: Activity,
        product: AffiseProduct,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        api
            ?.purchase(activity, product, type, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }

    /**
     * Subscription Module purchase product
     * (billingclient wrapper)
     */
    @JvmStatic
    fun purchase(
        activity: Activity,
        productId: String,
        offerToken: String?,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        api
            ?.purchase(activity, productId, offerToken, type, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }
}

@Deprecated(
    message = "Method moved to Affise.Module",
    replaceWith = ReplaceWith("Affise.Module.hasSubscriptionModule()")
)
fun Affise.hasSubscriptionModule(): Boolean {
    return Affise.Module.hasSubscriptionModule()
}

@Deprecated(
    message = "Method moved to Affise.Module",
    replaceWith = ReplaceWith("Affise.Module.fetchProducts(productsIds, callback)")
)
fun Affise.fetchProducts(
    productsIds: List<String>,
    callback: AffiseResultCallback<AffiseProductsResult>,
) {
    Affise.Module.fetchProducts(productsIds, callback)
}

@Deprecated(
    message = "Method moved to Affise.Module",
    replaceWith = ReplaceWith("Affise.Module.purchase(activity, product, type, callback)")
)
fun Affise.purchase(
    activity: Activity,
    product: AffiseProduct,
    type: AffiseProductType? = null,
    callback: AffiseResultCallback<AffisePurchasedInfo>,
) {
    Affise.Module.purchase(activity, product, type, callback)
}

@Deprecated(
    message = "Method moved to Affise.Module",
    replaceWith = ReplaceWith("Affise.Module.purchase(activity, productId, offerToken, type, callback)")
)
fun Affise.purchase(
    activity: Activity,
    productId: String,
    offerToken: String? = null,
    type: AffiseProductType? = null,
    callback: AffiseResultCallback<AffisePurchasedInfo>,
) {
    Affise.Module.purchase(activity, productId, offerToken, type, callback)
}

/**
 * Get Subscription Module status
 * (billingclient wrapper)
 */
fun Affise.Module.hasSubscriptionModule(): Boolean {
    return AffiseSubscription.hasSubscriptionModule()
}

/**
 * Subscription Module fetch products for purchase from [productsIds]
 * (billingclient wrapper)
 */
fun Affise.Module.fetchProducts(
    productsIds: List<String>, callback: AffiseResultCallback<AffiseProductsResult>,
) {
    AffiseSubscription.fetchProducts(productsIds, callback)
}

/**
 * Subscription Module purchase product
 * (billingclient wrapper)
 */
fun Affise.Module.purchase(
    activity: Activity,
    product: AffiseProduct,
    type: AffiseProductType? = null,
    callback: AffiseResultCallback<AffisePurchasedInfo>,
) {
    AffiseSubscription.purchase(activity, product, type, callback)
}

/**
 * Subscription Module purchase product
 * (billingclient wrapper)
 */
fun Affise.Module.purchase(
    activity: Activity,
    productId: String,
    offerToken: String? = null,
    type: AffiseProductType? = null,
    callback: AffiseResultCallback<AffisePurchasedInfo>,
) {
    AffiseSubscription.purchase(activity, productId, offerToken, type, callback)
}