package com.affise.attribution.modules.subscription

data class AffiseProductsResult(
    val products: List<AffiseProduct>,
    val invalidIds: List<String>
)