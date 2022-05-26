package com.affise.app.ui.fragments.home.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.R
import com.affise.app.databinding.ItemRecyclerPopularBinding
import com.affise.app.entity.ProductEntity
import com.bumptech.glide.Glide
import javax.inject.Inject

data class ProductPopularEntity(
    val product: ProductEntity,
    val inLike: Boolean,
    val inCart: Boolean
)

class AdapterPopular @Inject constructor(
) : ListAdapter<ProductPopularEntity, ProductPopularViewHolder>(ProductPopularCallback()) {

    var onClickProduct: (ProductPopularEntity) -> Unit = {}

    var onClickLike: (ProductPopularEntity) -> Unit = {}

    var onClickAdd: (ProductPopularEntity) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductPopularViewHolder = LayoutInflater.from(parent.context)
        .let {
            ItemRecyclerPopularBinding.inflate(it, parent, false)
        }
        .let {
            ProductPopularViewHolder(it, onClickProduct, onClickLike, onClickAdd)
        }

    override fun onBindViewHolder(holder: ProductPopularViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductPopularCallback : DiffUtil.ItemCallback<ProductPopularEntity>() {
    override fun areItemsTheSame(
        oldItem: ProductPopularEntity,
        newItem: ProductPopularEntity
    ): Boolean = oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(
        oldItem: ProductPopularEntity,
        newItem: ProductPopularEntity
    ): Boolean = oldItem.product.name == newItem.product.name &&
        oldItem.product.price == newItem.product.price &&
        oldItem.product.unit == newItem.product.unit &&
        oldItem.inCart == newItem.inCart &&
        oldItem.inLike == newItem.inLike
}

class ProductPopularViewHolder(
    private val binding: ItemRecyclerPopularBinding,
    private val onClickProduct: (ProductPopularEntity) -> Unit,
    private val onClickLike: (ProductPopularEntity) -> Unit,
    private val onClickAdd: (ProductPopularEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductPopularEntity) = with(binding) {
        Glide.with(imageView)
            .load(Uri.parse("file:///android_asset/${product.product.images.firstOrNull()}"))
            .error(R.drawable.img_product_test)
            .into(imageView)

        name.text = product.product.name

        price.text = product.product.price?.toPlainString()?.let { price ->
            product.product.unit?.let { unit ->
                "$unit$price"
            } ?: price
        } ?: ""

        like.setImageResource(
            if (product.inLike) R.drawable.ic_like_full else R.drawable.ic_like
        )

        add.setImageResource(
            if (product.inCart) R.drawable.ic_cart_full else R.drawable.ic_cart
        )

        main.setOnClickListener {
            onClickProduct.invoke(product)
        }

        like.setOnClickListener {
            onClickLike.invoke(product)
        }

        add.setOnClickListener {
            onClickAdd.invoke(product)
        }
    }
}