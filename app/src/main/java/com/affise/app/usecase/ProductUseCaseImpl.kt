package com.affise.app.usecase

import com.affise.app.entity.ProductEntity
import com.affise.app.repositories.ProductRepository
import com.affise.app.ui.fragments.cart.adapters.ProductCartEntity
import com.affise.app.ui.fragments.home.adapters.ProductNewEntity
import com.affise.app.ui.fragments.home.adapters.ProductPopularEntity
import com.affise.app.ui.fragments.likes.adapters.ProductLikeEntity
import javax.inject.Inject

class ProductUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : ProductUseCase {

    override suspend fun getPopularProducts(
    ): List<ProductPopularEntity> = repository.getPopularProducts()

    override suspend fun getNewProducts(): List<ProductNewEntity> = repository.getNewProducts()

    override suspend fun getProductDetails(
        id: Long
    ): ProductEntity? = repository.getProductDetails(id)

    override suspend fun getLikeProducts(): List<ProductLikeEntity> = repository.getLikeProducts()

    override suspend fun inLike(productId: Long): Boolean = repository.inLike(productId)

    override suspend fun inCart(productId: Long): Boolean = repository.inCart(productId)

    override suspend fun updateLike(productId: Long?): Boolean = repository.updateLike(productId)

    override suspend fun updateCart(productId: Long?): Boolean = repository.updateCart(productId)

    override suspend fun getCart(): List<ProductCartEntity> = repository.getCart()

    override suspend fun removeOnCart(
        productId: Long
    ): List<ProductCartEntity> = repository.removeOnCart(productId)

    override suspend fun addToCart(
        productId: Long
    ): List<ProductCartEntity> = repository.addToCart(productId)
}