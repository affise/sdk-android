package com.affise.attribution.module.subscription.store

import android.app.Activity
import android.content.Context
import com.affise.attribution.module.subscription.store.billing.ConnectionManager
import com.affise.attribution.module.subscription.store.billing.HistoryManager
import com.affise.attribution.module.subscription.store.billing.ProductManager
import com.affise.attribution.module.subscription.store.billing.TransactionManager
import com.affise.attribution.module.subscription.store.utils.setType
import com.affise.attribution.modules.subscription.AffiseProductType
import com.affise.attribution.modules.subscription.AffiseProductsResult
import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.affise.attribution.modules.subscription.AffiseResult
import com.affise.attribution.modules.subscription.AffiseResultCallback

internal class StoreManager(context: Context) {

    private val productManager: ProductManager by lazy {
        ProductManager(connectionManager)
    }

    private val historyManager: HistoryManager by lazy {
        HistoryManager(connectionManager)
    }

    private val transactionManager: TransactionManager by lazy {
        TransactionManager(connectionManager)
    }

    private val connectionManager: ConnectionManager by lazy {
        ConnectionManager(context)
    }

    init {
        connectionManager.purchaseUpdateListener = transactionManager
    }

    fun fetchProducts(
        productsIds: List<String>,
        callback: AffiseResultCallback<AffiseProductsResult>,
    ) {
        connectionManager.connection { result ->
            when (result) {
                is AffiseResult.Success -> productManager.queryProducts(productsIds, callback)
                is AffiseResult.Error -> callback.handle(result)
            }
        }
    }

    fun purchase(
        activity: Activity,
        productId: String,
        offerToken: String? = null,
        type: AffiseProductType?,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        connectionManager.connection { result ->
            when (result) {
                is AffiseResult.Error -> callback.handle(result)
                is AffiseResult.Success -> {
                    productManager.purchaseProduct(
                        activity,
                        productId,
                        offerToken
                    ) { purchase ->
                        when (purchase) {
                            is AffiseResult.Error -> callback.handle(purchase)
                            is AffiseResult.Success -> {
                                transactionManager.process(purchase.value.setType(type), callback)
                            }
                        }
                    }
                }
            }
        }
    }
}