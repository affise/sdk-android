package com.affise.app.storage

import com.affise.app.entity.Colors
import com.affise.app.entity.ProductEntity
import com.affise.app.ui.fragments.cart.adapters.ProductCartEntity
import com.affise.app.ui.fragments.details.adapters.SizeType
import com.affise.app.ui.fragments.home.adapters.ProductNewEntity
import com.affise.app.ui.fragments.home.adapters.ProductPopularEntity
import com.affise.app.ui.fragments.likes.adapters.ProductLikeEntity
import javax.inject.Inject

class FakeStorageImpl @Inject constructor() : Storage {

    private val product1 = ProductEntity(
        1,
        10.toBigDecimal(),
        "$",
        "Air Max 270 G",
        "The Nike Air max 270 G Huarache Run DNA ch. 1 reimagines not only the original Air",
        3.8,
        SizeType.US,
        9.0,
        Colors.BLACK,
        listOf("img1.png")
    )

    private val product2 = ProductEntity(
        2,
        20.toBigDecimal(),
        "$",
        "Nike Ultra Zoom",
        "The Nike Ultra Zoom Huarache Run DNA ch. 1 reimagines not only the original Air",
        4.8,
        SizeType.US,
        8.0,
        Colors.WHITE,
        listOf("img2.png", "img2.png")
    )

    private val product3 = ProductEntity(
        3,
        18.6.toBigDecimal(),
        "$",
        "Nike Power pro",
        "The Nike Ultra Zoom Huarache Run DNA ch. 1 reimagines not only the original Air",
        4.1,
        SizeType.US,
        7.0,
        Colors.BLUE,
        listOf("img3.png", "img3.png", "img3.png")
    )

    private val product4 = ProductEntity(
        4,
        999.toBigDecimal(),
        "$",
        "Nike Air",
        "The Nike Air Huarache Run DNA ch. 1 reimagines not only the original Air",
        2.1,
        SizeType.EURO,
        45.0,
        Colors.YELLOW,
        listOf("img4.png", "img4.png", "img4.png", "img4.png")
    )

    private val product5 = ProductEntity(
        5,
        202.22.toBigDecimal(),
        "$",
        "Nike Power",
        "The Nike Power Huarache Run DNA ch. 1 reimagines not only the original Air",
        4.6,
        SizeType.US,
        9.0,
        Colors.BLACK,
        listOf("img5.png", "img5.png", "img5.png", "img5.png", "img5.png")
    )

    private val allProducts = listOf(
        product1, product2, product3, product4, product5
    )

    private val popularProducts = mutableListOf(
        product1, product2, product3, product4, product5
    )

    private val newProducts = mutableListOf(
        product1, product2, product3, product4, product5
    )

    private val likeProducts = mutableListOf(
        product1, product3
    )

    private val cartProducts = mutableListOf(
        ProductCartEntity(product4, 1)
    )

    override suspend fun getPopularProducts(): List<ProductPopularEntity> = popularProducts
        .map {
            ProductPopularEntity(it, inLike(it.id), inCart(it.id))
        }

    override suspend fun getNewProducts(): List<ProductNewEntity> = newProducts
        .map {
            ProductNewEntity(it, inLike(it.id), inCart(it.id))
        }

    override suspend fun getLikeProducts(): List<ProductLikeEntity> = likeProducts
        .map {
            ProductLikeEntity(it, true, inCart(it.id))
        }

    override suspend fun getProductDetails(
        id: Long
    ): ProductEntity? = allProducts.find { it.id == id }

    override suspend fun inLike(productId: Long): Boolean = likeProducts
        .any { it.id == productId }

    override suspend fun inCart(productId: Long): Boolean = cartProducts
        .any { it.product.id == productId }

    override suspend fun updateLike(productId: Long?): Boolean {
        productId ?: return false

        likeProducts.find { it.id == productId }
            ?.let {
                likeProducts.remove(it)
            }
            ?: allProducts.find { it.id == productId }?.let {
                likeProducts.add(it)
            }

        return inLike(productId)
    }

    override suspend fun updateCart(productId: Long?): Boolean {
        productId ?: return false

        cartProducts.find { it.product.id == productId }
            ?.let {
                cartProducts.remove(it)
            }
            ?: allProducts.find { it.id == productId }?.let {
                cartProducts.add(ProductCartEntity(it, 1))
            }

        return inCart(productId)
    }

    override suspend fun getCart(): List<ProductCartEntity> = cartProducts
        .map { ProductCartEntity(it.product, it.count) }

    override suspend fun removeOnCart(productId: Long): List<ProductCartEntity> {
        cartProducts.find { it.product.id == productId }?.let {
            if (it.count > 1) {
                it.count = it.count - 1
            } else {
                cartProducts.remove(it)
            }
        }

        return getCart()
    }

    override suspend fun addToCart(productId: Long): List<ProductCartEntity> {
        cartProducts.find { it.product.id == productId }?.let {
            it.count = it.count + 1
        }

        return getCart()
    }
}