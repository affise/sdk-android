package com.affise.attribution.modules.subscription

import java.lang.Exception

sealed class AffiseSubscriptionError: Exception() {

    class NotInitialized : Exception("subscription module not initialized")
    class ProductNotFound(val ids: List<String>): Exception("product not found [${ids.joinToString(", ")}]")
    class PurchaseFailed(val error: Any): Exception("purchase failed: $error")
    class ConnectionError(val error: Any): Exception("connection error $error")
    class UserCanceled(val error: Any): Exception("user canceled $error")
    class BillingUnavailable(val error: Any): Exception("billing unavailable $error")
    class ItemUnavailable(val error: Any): Exception("item unavailable $error")
    class ItemAlreadyOwned(val error: Any): Exception("item already owned $error")
    class ItemNotOwned(val error: Any): Exception("item not owned $error")
}