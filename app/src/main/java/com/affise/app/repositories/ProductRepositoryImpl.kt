package com.affise.app.repositories

import com.affise.app.entity.ProductEntity
import com.affise.app.storage.Storage
import com.affise.app.ui.fragments.cart.adapters.ProductCartEntity
import com.affise.app.ui.fragments.home.adapters.ProductNewEntity
import com.affise.app.ui.fragments.home.adapters.ProductPopularEntity
import com.affise.app.ui.fragments.likes.adapters.ProductLikeEntity
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val storage: Storage
) : ProductRepository {

    override suspend fun getPopularProducts(
    ): List<ProductPopularEntity> = storage.getPopularProducts()

    override suspend fun getNewProducts(): List<ProductNewEntity> = storage.getNewProducts()

    override suspend fun getLikeProducts(): List<ProductLikeEntity> = storage.getLikeProducts()

    override suspend fun getProductDetails(id: Long): ProductEntity? = storage.getProductDetails(id)

    override suspend fun inLike(productId: Long): Boolean = storage.inLike(productId)

    override suspend fun inCart(productId: Long): Boolean = storage.inCart(productId)

    override suspend fun updateLike(productId: Long?): Boolean = storage.updateLike(productId)

    override suspend fun updateCart(productId: Long?): Boolean = storage.updateCart(productId)

    override suspend fun getCart(): List<ProductCartEntity> = storage.getCart()

    override suspend fun removeOnCart(
        productId: Long
    ): List<ProductCartEntity> = storage.removeOnCart(productId)

    override suspend fun addToCart(
        productId: Long
    ): List<ProductCartEntity> = storage.addToCart(productId)
}