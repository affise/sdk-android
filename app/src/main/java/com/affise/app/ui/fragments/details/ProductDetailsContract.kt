package com.affise.app.ui.fragments.details

import androidx.lifecycle.LiveData

interface ProductDetailsContract {
    interface ViewModel {
        val stateData: LiveData<ProductDetailsState>
        val currentGroup: LiveData<ProductDetailsActionGroup>

        fun setItemId(id: Long?)
        fun clickLike(id: Long?)
        fun selectGroup(group: ProductDetailsActionGroup)
        fun clickCart()
        fun clickBuyNow()
    }
}