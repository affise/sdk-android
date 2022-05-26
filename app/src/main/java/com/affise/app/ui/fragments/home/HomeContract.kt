package com.affise.app.ui.fragments.home

import androidx.lifecycle.LiveData
import com.affise.app.entity.ProductEntity

interface HomeContract {
    interface ViewModel {
        val stateData: LiveData<HomeState>

        fun setSearch(value: String)

        fun update()
        fun clickLike(productEntity: ProductEntity)
        fun clickAdd(productEntity: ProductEntity)
    }
}