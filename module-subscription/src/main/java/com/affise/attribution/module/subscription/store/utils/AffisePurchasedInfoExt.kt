package com.affise.attribution.module.subscription.store.utils

import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.android.billingclient.api.Purchase

fun AffisePurchasedInfo.getPurchase(): Purchase? = this.purchase as? Purchase
