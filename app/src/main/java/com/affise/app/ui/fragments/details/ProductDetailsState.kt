package com.affise.app.ui.fragments.details

import com.affise.app.entity.ProductEntity

sealed class ProductDetailsState {
    object LoadingState : ProductDetailsState()
    object UpdateState : ProductDetailsState()
    data class ErrorState(val retry: Boolean) : ProductDetailsState()
    data class DataState(
        val productsDetails: ProductEntity,
        val inLike: Boolean,
        val inCart: Boolean
    ) : ProductDetailsState()
}