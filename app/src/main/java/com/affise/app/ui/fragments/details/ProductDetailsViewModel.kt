package com.affise.app.ui.fragments.details

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affise.app.entity.ProductEntity
import com.affise.app.extensions.IOWithErrorHandling
import com.affise.app.usecase.ProductUseCase
import com.affise.attribution.Affise
import com.affise.attribution.events.predefined.AddToCartEvent
import com.affise.attribution.events.predefined.AddToWishlistEvent
import com.affise.attribution.events.predefined.ViewItemEvent
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

enum class ProductDetailsActionGroup {
    COLORS,
    SIZE,
    REVIEWS
}

class ProductDetailsViewModel @Inject constructor(
    private val useCase: ProductUseCase,
    private val objectMapper: ObjectMapper
) : ViewModel(), ProductDetailsContract.ViewModel {

    private var currentProductId: Long? = null

    private val isLike = MutableLiveData<Boolean>()

    private val isCart = MutableLiveData<Boolean>()

    private val product = MutableLiveData<ProductEntity>()

    override val stateData = MediatorLiveData<ProductDetailsState>().apply {
        val observe: (Any) -> Unit = {
            val inLike = isLike.value
            val inCart = isCart.value
            val product = product.value

            if (inLike != null && inCart != null && product != null) {
                postValue(ProductDetailsState.DataState(product, inLike, inCart))
            }
        }

        addSource(isLike, observe)
        addSource(isCart, observe)
        addSource(product, observe)
    }

    override val currentGroup = MutableLiveData(ProductDetailsActionGroup.SIZE)

    override fun setItemId(id: Long?) {
        currentProductId = id

        if (id == null) {
            stateData.postValue(ProductDetailsState.ErrorState(false))

            return
        }

        stateData.value = if (stateData.value is ProductDetailsState.DataState) {
            ProductDetailsState.UpdateState
        } else {
            ProductDetailsState.LoadingState
        }

        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
            stateData.postValue(ProductDetailsState.ErrorState(true))
        }) {
            useCase.getProductDetails(id)?.let {
                val productData = it
                val likeData = useCase.inLike(id)
                val cartData = useCase.inCart(id)

                Affise.sendEvent(
                    ViewItemEvent(
                        JSONObject().apply {
                            put("product", JSONObject(objectMapper.writeValueAsString(productData)))
                            put("isLike", likeData)
                            put("inCart", cartData)
                        },
                        "product details"
                    )
                )

                product.postValue(productData)
                isLike.postValue(likeData)
                isCart.postValue(cartData)
            } ?: stateData.postValue(ProductDetailsState.ErrorState(false))
        }
    }

    override fun selectGroup(group: ProductDetailsActionGroup) {
        if (currentGroup.value != group) {
            currentGroup.postValue(group)
        }
    }

    override fun clickLike(id: Long?) {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            (stateData.value as? ProductDetailsState.DataState)?.let {
                Affise.sendEvent(
                    AddToWishlistEvent(
                        JSONObject(objectMapper.writeValueAsString(it.productsDetails)),
                        System.currentTimeMillis(),
                        "Add to wishlist in Product details"
                    )
                )
            }

            isLike.postValue(useCase.updateLike(currentProductId))
        }
    }

    override fun clickCart() {
        viewModelScope.launch(Dispatchers.IOWithErrorHandling {
        }) {
            (stateData.value as? ProductDetailsState.DataState)?.let {
                Affise.sendEvent(
                    AddToCartEvent(
                        JSONObject(objectMapper.writeValueAsString(it.productsDetails)),
                        System.currentTimeMillis(),
                        "Add to cart in Product details"
                    )
                )
            }

            isCart.postValue(useCase.updateCart(currentProductId))
        }
    }

    override fun clickBuyNow() {
    }
}
