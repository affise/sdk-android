package com.affise.app.entity

import com.affise.app.ui.fragments.details.adapters.SizeType
import java.math.BigDecimal

data class ProductEntity(
    val id: Long,
    val price: BigDecimal?,
    val unit: String?,
    val name: String,
    val description: String,
    val rating: Double,
    val size_type: SizeType,
    val size: Double,
    val color: Colors,
    val images: List<String>
)