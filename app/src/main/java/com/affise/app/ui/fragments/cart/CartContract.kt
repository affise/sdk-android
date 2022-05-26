package com.affise.app.ui.fragments.cart

import androidx.lifecycle.LiveData
import com.affise.app.entity.ProductEntity

interface CartContract {
    interface ViewModel {
        val stateData: LiveData<CartState>

        fun update()
        fun addProduct(product: ProductEntity)
        fun removeProduct(product: ProductEntity)
    }
}