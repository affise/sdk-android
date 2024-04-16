package com.affise.attribution.module.subscription.store.billing

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.PurchaseHistoryResult
import com.android.billingclient.api.PurchasesResult
import com.android.billingclient.api.QueryPurchaseHistoryParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.queryPurchaseHistory
import com.android.billingclient.api.queryPurchasesAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

internal class HistoryManager(
    private val connectionManager: ConnectionManager
) {
    private val billingClient: BillingClient
        get() = connectionManager.billingClient

    /**
     * Get Purchase History of all types
     */
    suspend fun queryPurchaseHistoryAll(): List<PurchaseHistoryResult> {
        return withContext(Dispatchers.IO) {
            listOf(
                async {
                    queryPurchaseHistory(BillingClient.ProductType.INAPP)
                },
                async {
                    queryPurchaseHistory(BillingClient.ProductType.SUBS)
                }
            )
        }.awaitAll()
    }

    suspend fun queryPurchaseHistory(@BillingClient.ProductType type: String): PurchaseHistoryResult {
        val purchaseHistoryParams = QueryPurchaseHistoryParams
            .newBuilder()
            .setProductType(type)
            .build()

        return billingClient.queryPurchaseHistory(purchaseHistoryParams)
    }

    /**
     * Get Owned Purchase of all types
     */
    suspend fun queryOwnedPurchasesAll(): List<PurchasesResult> {
        return withContext(Dispatchers.IO) {
            listOf(
                async {
                    queryOwnedPurchases(BillingClient.ProductType.INAPP)
                },
                async {
                    queryOwnedPurchases(BillingClient.ProductType.SUBS)
                }
            )
        }.awaitAll()
    }

    private suspend fun queryOwnedPurchases(@BillingClient.ProductType type: String): PurchasesResult {
        val queryPurchasesParams = QueryPurchasesParams
            .newBuilder()
            .setProductType(type)
            .build()

        return billingClient.queryPurchasesAsync(queryPurchasesParams)
    }
}