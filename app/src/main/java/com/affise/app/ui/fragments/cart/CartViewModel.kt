package com.affise.app.ui.fragments.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.IOWithErrorHandling
import com.affise.app.usecase.ProductUseCase
import com.affise.attribution.Affise
import com.affise.attribution.events.predefined.ViewCartEvent
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val useCase: ProductUseCase,
    private val objectMapper: ObjectMapper
) : ViewModel(), CartContract.ViewModel {

    override val stateData = MutableLiveData<CartState>()

    init {
        updateData()
    }

    override fun update() {
        val currentState = stateData.value
        if (currentState == CartState.LoadingState || currentState == CartState.UpdateState) {
            return
        }
        stateData.value = CartState.UpdateState

        updateData()
    }

    private fun updateData() {
        stateData.value = CartState.UpdateState

        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
            stateData.postValue(CartState.ErrorState)
        }) {
            delay(300)

            val items = useCase.getCart()

            val itemsCart = items
                .map { objectMapper.writeValueAsString(it) }
                .map { JSONObject(it) }

            Affise.sendEvent(
                ViewCartEvent(
                    JSONObject().apply {
                        put("cart", JSONArray(itemsCart))
                    },
                    "Cart"
                )
            )

            stateData.postValue(
                CartState.DataState(items)
            )
        }
    }

    override fun addProduct(product: ProductEntity) {
        stateData.value = CartState.UpdateState

        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            stateData.postValue(
                CartState.DataState(useCase.addToCart(product.id))
            )
        }
    }

    override fun removeProduct(product: ProductEntity) {
        stateData.value = CartState.UpdateState

        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            stateData.postValue(
                CartState.DataState(useCase.removeOnCart(product.id))
            )
        }
    }
}