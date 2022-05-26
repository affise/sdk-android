package com.affise.app.ui.fragments.home

import com.affise.app.ui.fragments.home.adapters.ProductNewEntity
import com.affise.app.ui.fragments.home.adapters.ProductPopularEntity

sealed class HomeState {
    object LoadingState : HomeState()
    object UpdateState : HomeState()
    object ErrorState : HomeState()
    data class DataState(
        val popularProducts: List<ProductPopularEntity>,
        val newProducts: List<ProductNewEntity>,
    ) : HomeState()
}