package com.affise.app.ui.fragments.likes.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.R
import com.affise.app.databinding.ItemRecyclerLikeBinding
import com.affise.app.entity.ProductEntity
import com.bumptech.glide.Glide
import javax.inject.Inject

data class ProductLikeEntity(
    val product: ProductEntity,
    val inLike: Boolean,
    val inCart: Boolean
)

class AdapterLikes @Inject constructor(
) : ListAdapter<ProductLikeEntity, ProductLikeViewHolder>(ProductLikeCallback()) {

    var onClickProduct: (ProductLikeEntity) -> Unit = {}

    var onClickLike: (ProductLikeEntity) -> Unit = {}

    var onClickAdd: (ProductLikeEntity) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductLikeViewHolder = LayoutInflater.from(parent.context)
        .let {
            ItemRecyclerLikeBinding.inflate(it, parent, false)
        }
        .let {
            ProductLikeViewHolder(it, onClickProduct, onClickLike, onClickAdd)
        }

    override fun onBindViewHolder(holder: ProductLikeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductLikeCallback : DiffUtil.ItemCallback<ProductLikeEntity>() {
    override fun areItemsTheSame(
        oldItem: ProductLikeEntity,
        newItem: ProductLikeEntity
    ): Boolean = oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(
        oldItem: ProductLikeEntity,
        newItem: ProductLikeEntity
    ): Boolean = oldItem.product.name == newItem.product.name &&
        oldItem.product.price == newItem.product.price &&
        oldItem.product.unit == newItem.product.unit &&
        oldItem.inCart == newItem.inCart &&
        oldItem.inLike == newItem.inLike
}

class ProductLikeViewHolder(
    private val binding: ItemRecyclerLikeBinding,
    private val onClickProduct: (ProductLikeEntity) -> Unit,
    private val onClickLike: (ProductLikeEntity) -> Unit,
    private val onClickAdd: (ProductLikeEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductLikeEntity) = with(binding) {
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