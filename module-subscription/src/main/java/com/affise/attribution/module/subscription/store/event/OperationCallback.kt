package com.affise.attribution.module.subscription.store.event

import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.affise.attribution.modules.subscription.AffiseResultCallback

internal data class OperationCallback(
    val callback: AffiseResultCallback<AffisePurchasedInfo>,
    val product: AffiseProduct,
)
