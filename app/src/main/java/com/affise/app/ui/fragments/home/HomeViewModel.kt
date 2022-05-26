package com.affise.app.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.IOWithErrorHandling
import com.affise.app.usecase.ProductUseCase
import com.affise.attribution.Affise
import com.affise.attribution.events.predefined.AddToCartEvent
import com.affise.attribution.events.predefined.AddToWishlistEvent
import com.affise.attribution.events.predefined.SearchEvent
import com.affise.attribution.events.predefined.ViewItemsEvent
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val useCase: ProductUseCase,
    private val objectMapper: ObjectMapper
) : ViewModel(), HomeContract.ViewModel {

    override val stateData = MutableLiveData<HomeState>(HomeState.LoadingState)

    init {
        updateData()
    }

    override fun update() {
        val currentState = stateData.value
        if (currentState == HomeState.LoadingState || currentState == HomeState.UpdateState) {
            return
        }

        stateData.value = HomeState.UpdateState

        updateData()
    }

    private fun updateData() {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
            stateData.postValue(HomeState.ErrorState)
        }) {
            delay(300)
            updateProducts()
        }
    }

    override fun setSearch(value: String) {
        Affise.sendEvent(
            SearchEvent(JSONArray(), System.currentTimeMillis(), "search by '$value'")
        )
    }

    override fun clickLike(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            Affise.sendEvent(
                AddToWishlistEvent(
                    JSONObject(objectMapper.writeValueAsString(productEntity)),
                    System.currentTimeMillis(),
                    "Add to wishlist on home"
                )
            )

            useCase.updateLike(productEntity.id)
            updateProducts()
        }
    }

    override fun clickAdd(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            Affise.sendEvent(
                AddToCartEvent(
                    JSONObject(objectMapper.writeValueAsString(productEntity)),
                    System.currentTimeMillis(),
                    "Add to cart on home"
                )
            )


            useCase.updateCart(productEntity.id)
            updateProducts()
        }
    }

    private suspend fun updateProducts() {
        val popularProducts = useCase.getPopularProducts()

        val newProducts = useCase.getNewProducts()

        val itemsPopular = popularProducts
            .map { objectMapper.writeValueAsString(it) }
            .map { JSONObject(it) }

        val itemsNew = newProducts
            .map { objectMapper.writeValueAsString(it) }
            .map { JSONObject(it) }

        Affise.sendEvent(
            ViewItemsEvent(JSONArray(itemsPopular), "popularProducts")
        )

        Affise.sendEvent(
            ViewItemsEvent(JSONArray(itemsNew), "newProducts")
        )

        stateData.postValue(
            HomeState.DataState(popularProducts, newProducts)
        )
    }
}