package com.affise.attribution.module.subscription.store.billing

import android.content.Context
import com.affise.attribution.modules.subscription.AffiseResult
import com.affise.attribution.modules.subscription.AffiseSubscriptionError
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.Closeable
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


internal class ConnectionManager(
    context: Context,
) : PurchasesUpdatedListener, Closeable {

    var purchaseUpdateListener: PurchasesUpdatedListener? = null

    val billingClient: BillingClient = BillingClient
        .newBuilder(context)
        .enablePendingPurchases()
        .setListener(this)
        .build()


    private val mutex = Mutex()

    @OptIn(DelicateCoroutinesApi::class)
    fun connection(status: suspend (AffiseResult<Boolean>) -> Unit) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                mutex.withLock {
                    if (billingClient.isReady) {
                        status(AffiseResult.Success(true))
                    } else {
                        status(billingClient.connect())
                    }
                }
            }
        }
    }

    private suspend fun BillingClient.connect(): AffiseResult<Boolean> {
        return suspendCoroutine { continuation ->
            startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    when (billingResult.responseCode) {
                        BillingClient.BillingResponseCode.OK -> {
                            continuation.resume(AffiseResult.Success(true))
                        }

                        else -> {
                            continuation.resume(
                                AffiseResult.Error(
                                    AffiseSubscriptionError.ConnectionError(billingResult.toString())
                                )
                            )
                        }
                    }
                }

                override fun onBillingServiceDisconnected() {
                    continuation.resume(
                        AffiseResult.Error(AffiseSubscriptionError.ConnectionError("BillingServiceDisconnected"))
                    )
                }
            })
        }
    }

    fun endConnection() {
        billingClient.endConnection()
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?,
    ) {
        purchaseUpdateListener?.onPurchasesUpdated(billingResult, purchases)
    }

    override fun close() {
        if (billingClient.isReady) {
            endConnection()
        }
    }
}