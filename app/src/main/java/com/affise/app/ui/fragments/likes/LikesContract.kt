package com.affise.app.ui.fragments.likes

import androidx.lifecycle.LiveData
import com.affise.app.entity.ProductEntity

interface LikesContract {

    interface ViewModel {
        val stateData: LiveData<LikesState>

        fun update()
        fun clickLike(product: ProductEntity)
        fun clickAdd(product: ProductEntity)
    }
}