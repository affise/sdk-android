package com.affise.app.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.IOWithErrorHandling
import com.affise.app.usecase.ProductUseCase
import com.affise.attribution.events.parameters.PredefinedListObject
import com.affise.attribution.events.parameters.PredefinedObject
import com.affise.attribution.events.predefined.AddToCartEvent
import com.affise.attribution.events.predefined.AddToWishlistEvent
import com.affise.attribution.events.predefined.SearchEvent
import com.affise.attribution.events.predefined.ViewItemsEvent
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        SearchEvent("search by '$value'")
            .send()
    }

    override fun clickLike(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            AddToWishlistEvent("Add to wishlist on home")
                .addPredefinedParameter(
                    PredefinedObject.CONTENT,
                    JSONObject(objectMapper.writeValueAsString(productEntity))
                )
                .send()

            useCase.updateLike(productEntity.id)
            updateProducts()
        }
    }

    override fun clickAdd(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            AddToCartEvent("Add to cart on home")
                .addPredefinedParameter(
                    PredefinedObject.CONTENT,
                    JSONObject(objectMapper.writeValueAsString(productEntity))
                )
                .send()


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

        ViewItemsEvent("popularProducts")
            .addPredefinedParameter(PredefinedListObject.CONTENT_LIST, itemsPopular)
            .send()

        ViewItemsEvent("newProducts")
            .addPredefinedParameter(PredefinedListObject.CONTENT_LIST, itemsNew)
            .send()

        stateData.postValue(
            HomeState.DataState(popularProducts, newProducts)
        )
    }
}