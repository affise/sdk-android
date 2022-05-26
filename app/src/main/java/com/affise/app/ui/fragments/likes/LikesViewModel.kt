package com.affise.app.ui.fragments.likes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.IOWithErrorHandling
import com.affise.app.usecase.ProductUseCase
import com.affise.attribution.Affise
import com.affise.attribution.events.predefined.AddToCartEvent
import com.affise.attribution.events.predefined.AddToWishlistEvent
import com.affise.attribution.events.predefined.ViewItemsEvent
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class LikesViewModel @Inject constructor(
    private val useCase: ProductUseCase,
    private val objectMapper: ObjectMapper
) : ViewModel(), LikesContract.ViewModel {

    override val stateData = MutableLiveData<LikesState>(LikesState.LoadingState)

    init {
        updateData()
    }

    override fun update() {
        val currentState = stateData.value
        if (currentState == LikesState.LoadingState || currentState == LikesState.UpdateState) {
            return
        }
        stateData.value = LikesState.UpdateState

        updateData()
    }

    private fun updateData() {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
            stateData.postValue(LikesState.ErrorState)
        }) {
            delay(300)

            updateProducts()
        }
    }

    override fun clickLike(product: ProductEntity) {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            Affise.sendEvent(
                AddToWishlistEvent(
                    JSONObject(objectMapper.writeValueAsString(product)),
                    System.currentTimeMillis(),
                    "Add to wishlist in Product details"
                )
            )

            useCase.updateLike(product.id)
            updateProducts()
        }
    }

    override fun clickAdd(product: ProductEntity) {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            Affise.sendEvent(
                AddToCartEvent(
                    JSONObject(objectMapper.writeValueAsString(product)),
                    System.currentTimeMillis(),
                    "Add to cart in Product details"
                )
            )

            useCase.updateCart(product.id)
            updateProducts()
        }
    }

    private suspend fun updateProducts() {
        val likeProducts = useCase.getLikeProducts()

        val itemsWishlist = likeProducts
            .map { objectMapper.writeValueAsString(it) }
            .map { JSONObject(it) }

        Affise.sendEvent(
            ViewItemsEvent(JSONArray(itemsWishlist), "itemsWishlist")
        )

        stateData.postValue(
            LikesState.DataState(likeProducts)
        )
    }
}