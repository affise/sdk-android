package com.affise.attribution.module.subscription.store.billing

import com.affise.attribution.module.subscription.store.event.OperationCallback
import com.affise.attribution.module.subscription.store.event.OperationEvent
import com.affise.attribution.module.subscription.store.utils.affiseError
import com.affise.attribution.module.subscription.store.utils.getFirstProductId
import com.affise.attribution.module.subscription.store.utils.getOriginalOrderId
import com.affise.attribution.module.subscription.store.utils.isOk
import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductType
import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.affise.attribution.modules.subscription.AffiseResult
import com.affise.attribution.modules.subscription.AffiseResultCallback
import com.affise.attribution.modules.subscription.AffiseSubscriptionError
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ConsumeResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.Purchase.PurchaseState.PURCHASED
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.consumePurchase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


internal class TransactionManager(
    private val connectionManager: ConnectionManager,
) : PurchasesUpdatedListener {

    private val billingClient: BillingClient
        get() = connectionManager.billingClient

    private val callbacks: MutableMap<String, OperationCallback> = mutableMapOf()

    @Synchronized
    fun process(
        product: AffiseProduct,
        callback: AffiseResultCallback<AffisePurchasedInfo>,
    ) {
        callbacks[product.productId] = OperationCallback(callback, product)
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?,
    ) {
        if (purchases.isNullOrEmpty()) {
            handleEmptyPurchases(billingResult)
            return
        }

        if (billingResult.isOk()) {
            handlePurchases(purchases, billingResult)
        } else {
            for (purchase in purchases) {
                handleFiledPurchase(purchase, billingResult)
            }
        }
    }

    @Synchronized
    private fun handleEmptyPurchases(billingResult: BillingResult) {
        val error = AffiseSubscriptionError
            .PurchaseFailed(billingResult.affiseError() ?: billingResult)

        val iterator = callbacks.entries.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            item.value.callback.handle(AffiseResult.Error(error))

            OperationEvent
                .create(
                    purchase = null,
                    product = item.value.product,
                    billingResult = billingResult,
                    failed = true
                )
                .send()

            iterator.remove()
        }
    }

    @Synchronized
    private fun handleCallback(purchase: Purchase, block: (OperationCallback) -> Unit) {
        val productId = purchase.getFirstProductId() ?: return
        callbacks[productId]?.let {
            block(it)
            callbacks.remove(productId)
        }
    }

    private fun getOperation(purchase: Purchase): OperationCallback? {
        val productId = purchase.getFirstProductId() ?: return null
        return callbacks[productId]
    }

    private fun handleFiledPurchase(purchase: Purchase, billingResult: BillingResult) {
        handleCallback(purchase) {
            val error = AffiseSubscriptionError
                .PurchaseFailed(billingResult.affiseError() ?: billingResult)

            it.callback.handle(AffiseResult.Error(error))

            OperationEvent
                .create(
                    purchase = purchase,
                    product = it.product,
                    billingResult = billingResult,
                    failed = true
                )
                .send()
        }
    }

    private fun handleSuccess(purchase: Purchase, billingResult: BillingResult) {
        handleCallback(purchase) {
            AffisePurchasedInfo(
                product = it.product,
                orderId = purchase.orderId,
                originalOrderId = purchase.getOriginalOrderId(),
                purchase = purchase
            ).also { info ->
                it.callback.handle(AffiseResult.Success(info))
            }

            OperationEvent
                .create(
                    purchase = purchase,
                    product = it.product,
                    billingResult = billingResult,
                    failed = false
                )
                .send()
        }
    }

    private fun handlePurchases(purchases: List<Purchase>, billingResult: BillingResult) {
        GlobalScope.launch {
//            withContext(Dispatchers.IO) {
            for (purchase in purchases) {
                acknowledgeAndConsume(purchase, billingResult)
            }
//            }
        }
    }

    private suspend fun acknowledgeAndConsume(purchase: Purchase, billingResult: BillingResult) {
        if (purchase.purchaseState != PURCHASED) return
        if (purchase.isAcknowledged) return

        val acknowledgeResult = acknowledge(purchase)
        if (acknowledgeResult.isOk()) {
            val operation = getOperation(purchase)
            if (operation?.product?.productType == AffiseProductType.NON_CONSUMABLE) {
                handleSuccess(purchase, acknowledgeResult)
            } else {
                val consumeResult = consume(purchase)
                if (consumeResult.isOk()) {
                    handleSuccess(purchase, consumeResult.billingResult)
                } else {
                    handleFiledPurchase(purchase, consumeResult.billingResult)
                }
            }
        } else {
            handleFiledPurchase(purchase, acknowledgeResult)
        }
    }

    private suspend fun acknowledge(purchase: Purchase): BillingResult {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams
            .newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        return billingClient.acknowledgePurchase(acknowledgePurchaseParams)
    }

    private suspend fun consume(purchase: Purchase): ConsumeResult {
        val consumeParams = ConsumeParams
            .newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        return billingClient.consumePurchase(consumeParams)
    }
}