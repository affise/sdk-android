package com.affise.attribution.module.subscription.store.billing

import android.app.Activity
import com.affise.attribution.module.subscription.store.utils.getProductDetails
import com.affise.attribution.module.subscription.store.utils.isOk
import com.affise.attribution.module.subscription.store.utils.toAffiseProduct
import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductsResult
import com.affise.attribution.modules.subscription.AffiseResult
import com.affise.attribution.modules.subscription.AffiseResultCallback
import com.affise.attribution.modules.subscription.AffiseSubscriptionError
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetailsResult
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.queryProductDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

internal class ProductManager(
    private val connectionManager: ConnectionManager,
) {
    private val billingClient: BillingClient
        get() = connectionManager.billingClient

    var productsDetails: MutableMap<String, List<AffiseProduct>> = mutableMapOf()
        private set

    suspend fun queryProducts(
        productIds: List<String>,
        callback: AffiseResultCallback<AffiseProductsResult>,
    ) {
        val queryResult = queryProductsAll(productIds)
        val productDetailsList = queryResult.mapNotNull {
            it.productDetailsList
        }.flatten()

        if (productDetailsList.isEmpty()) {
            val errorMessage = queryResult.firstOrNull { !it.isOk() }?.billingResult?.toString()

            val error = if (!errorMessage.isNullOrEmpty()) {
                AffiseResult.Error(Exception(errorMessage))
            } else {
                AffiseResult.Error(AffiseSubscriptionError.ProductNotFound(productIds))
            }
            callback.handle(error)
            return
        }

        val products = productDetailsList.associate {
            it.productId to it.toAffiseProduct()
        }.also {
            productsDetails.putAll(it)
        }

        val invalidIds = productIds.filterNot { products.keys.contains(it) }

        callback.handle(
            AffiseResult.Success(
                AffiseProductsResult(
                    products = products.values.flatten(),
                    invalidIds = invalidIds
                )
            )
        )
    }

    private suspend fun queryProductsAll(productIds: List<String>): List<ProductDetailsResult> {
        return withContext(Dispatchers.IO) {
            listOf(
                async {
                    queryProductsType(productIds, BillingClient.ProductType.INAPP)
                },
                async {
                    queryProductsType(productIds, BillingClient.ProductType.SUBS)
                }
            )
        }.awaitAll()
    }

    private suspend fun queryProductsType(
        productIds: List<String>,
        @BillingClient.ProductType productType: String,
    ): ProductDetailsResult {
        val productList = productIds.map { productId ->
            QueryProductDetailsParams
                .Product
                .newBuilder()
                .setProductId(productId)
                .setProductType(productType)
                .build()
        }

        val queryProductDetailsParams = QueryProductDetailsParams
            .newBuilder()
            .setProductList(productList)
            .build()

        return billingClient.queryProductDetails(queryProductDetailsParams)
    }

    fun purchaseProduct(
        activity: Activity,
        productId: String,
        offerToken: String? = null,
        callback: AffiseResultCallback<AffiseProduct>,
    ) {
        val purchaseResult = purchaseProductResult(activity, productId, offerToken)

        val products = productsDetails[productId]
        val product = products?.firstOrNull { it.subscription?.offerToken == offerToken }
            ?: products?.firstOrNull()

        val result = when (purchaseResult) {
            is AffiseResult.Error -> purchaseResult
            is AffiseResult.Success -> {
                if (product == null) {
                    AffiseResult.Error(AffiseSubscriptionError.ProductNotFound(listOf(productId)))
                } else {
                    AffiseResult.Success(product)
                }
            }
        }

        callback.handle(result)
    }

    private fun purchaseProductResult(
        activity: Activity,
        productId: String,
        offerToken: String?,
    ): AffiseResult<BillingResult> {
        val products = productsDetails[productId]
        val productDetails = products?.firstOrNull { it.subscription?.offerToken == offerToken }
            ?: products?.firstOrNull()


        if (productDetails == null) {
            return AffiseResult.Error(AffiseSubscriptionError.ProductNotFound(listOf(productId)))
        }

        // buy one product to receive one purchase.productId in onPurchasesUpdated
        val productDetailsParams = try {
            BillingFlowParams
                .ProductDetailsParams
                .newBuilder()
                .apply {
                    productDetails.getProductDetails()?.let(::setProductDetails)
                    offerToken?.let(::setOfferToken)
                }
                .build()
        } catch (e: Exception) {
            return AffiseResult.Error(e)
        }

        val billingFlowParams = BillingFlowParams
            .newBuilder()
            .setProductDetailsParamsList(listOf(productDetailsParams))
            .build()

        val result = billingClient.launchBillingFlow(activity, billingFlowParams)

        return when {
            result.isOk() -> AffiseResult.Success(result)
            else -> AffiseResult.Error(Exception(result.toString()))
        }
    }
}