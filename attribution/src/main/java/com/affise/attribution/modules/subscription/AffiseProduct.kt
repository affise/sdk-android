package com.affise.attribution.modules.subscription

data class AffiseProduct(
    val productId: String,
    val title: String,
    val description: String,

    var productType: AffiseProductType,

    val price: AffiseProductPrice?,
    val subscription: AffiseProductSubscriptionDetail?,

    val productDetails: Any?,
) {
    fun asConsumable(): AffiseProduct  = this.apply {
        productType = when (this.productType) {
            AffiseProductType.NON_CONSUMABLE -> AffiseProductType.CONSUMABLE
            else -> this.productType
        }
    }

    fun asNonConsumable(): AffiseProduct = this.apply {
        productType = when (this.productType) {
            AffiseProductType.CONSUMABLE -> AffiseProductType.NON_CONSUMABLE
            else -> this.productType
        }
    }
}


