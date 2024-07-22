package com.affise.attribution.module.subscription

import android.app.Activity
import com.affise.attribution.module.subscription.store.StoreManager
import com.affise.attribution.module.subscription.store.utils.setType
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductType
import com.affise.attribution.modules.subscription.AffiseProductsResult
import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.affise.attribution.modules.subscription.AffiseResult
import com.affise.attribution.modules.subscription.AffiseResultCallback
import com.affise.attribution.modules.subscription.AffiseSubscriptionApi
import com.affise.attribution.modules.subscription.AffiseSubscriptionError

internal class SubscriptionModule : AffiseModule(), AffiseSubscriptionApi {

    override val version: String = BuildConfig.AFFISE_VERSION

    private var storeManager: StoreManager? = null

    override fun start() {
        application?.let { app ->
            storeManager = StoreManager(app)
        }
    }

    override fun fetchProducts(
        productsIds: List<String>,
        callback: AffiseResultCallback<AffiseProductsResult>,
    ) {
        storeManager?.fetchProducts(productsIds, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }

    override fun purchase(
        activity: Activity,
        product: AffiseProduct,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        product.setType(type)
        purchase(
            activity,
            product.productId,
            product.subscription?.offerToken,
            product.productType,
            callback
        )
    }

    override fun purchase(
        activity: Activity,
        productId: String,
        offerToken: String?,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        storeManager?.purchase(activity, productId, offerToken, type, callback)
            ?: callback.handle(AffiseResult.Error(AffiseSubscriptionError.NotInitialized()))
    }
}