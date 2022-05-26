package com.affise.app.ui.fragments.cart

import com.affise.app.ui.fragments.cart.adapters.ProductCartEntity

sealed class CartState {
    object LoadingState : CartState()
    object UpdateState : CartState()
    object ErrorState : CartState()
    data class DataState(
        val cartProducts: List<ProductCartEntity>
    ) : CartState()
}