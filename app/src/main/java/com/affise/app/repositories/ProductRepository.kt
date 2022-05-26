package com.affise.app.repositories

import com.affise.app.entity.ProductEntity
import com.affise.app.ui.fragments.cart.adapters.ProductCartEntity
import com.affise.app.ui.fragments.home.adapters.ProductNewEntity
import com.affise.app.ui.fragments.home.adapters.ProductPopularEntity
import com.affise.app.ui.fragments.likes.adapters.ProductLikeEntity

interface ProductRepository {
    suspend fun getPopularProducts(): List<ProductPopularEntity>
    suspend fun getNewProducts(): List<ProductNewEntity>
    suspend fun getLikeProducts(): List<ProductLikeEntity>

    suspend fun getProductDetails(id: Long): ProductEntity?

    suspend fun inLike(productId: Long): Boolean
    suspend fun inCart(productId: Long): Boolean

    suspend fun updateLike(productId: Long?): Boolean
    suspend fun updateCart(productId: Long?): Boolean

    suspend fun getCart(): List<ProductCartEntity>
    suspend fun removeOnCart(productId: Long): List<ProductCartEntity>
    suspend fun addToCart(productId: Long): List<ProductCartEntity>
}