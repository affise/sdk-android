package com.affise.app.ui.fragments.home.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.R
import com.affise.app.databinding.ItemRecyclerNewBinding
import com.affise.app.entity.ProductEntity
import com.bumptech.glide.Glide
import javax.inject.Inject

data class ProductNewEntity(
    val product: ProductEntity,
    val inLike: Boolean,
    val inCart: Boolean
)

class AdapterNew @Inject constructor(
) : ListAdapter<ProductNewEntity, ProductNewViewHolder>(ProductNewCallback()) {

    var onClickProduct: (ProductNewEntity) -> Unit = {}

    var onClickLike: (ProductNewEntity) -> Unit = {}

    var onClickAdd: (ProductNewEntity) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductNewViewHolder = LayoutInflater.from(parent.context)
        .let {
            ItemRecyclerNewBinding.inflate(it, parent, false)
        }
        .let {
            ProductNewViewHolder(it, onClickProduct, onClickLike, onClickAdd)
        }

    override fun onBindViewHolder(holder: ProductNewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductNewCallback : DiffUtil.ItemCallback<ProductNewEntity>() {
    override fun areItemsTheSame(
        oldItem: ProductNewEntity,
        newItem: ProductNewEntity
    ): Boolean = oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(
        oldItem: ProductNewEntity,
        newItem: ProductNewEntity
    ): Boolean = oldItem.product.name == newItem.product.name &&
        oldItem.product.price == newItem.product.price &&
        oldItem.product.unit == newItem.product.unit &&
        oldItem.inCart == newItem.inCart &&
        oldItem.inLike == newItem.inLike
}

class ProductNewViewHolder(
    private val binding: ItemRecyclerNewBinding,
    private val onClickProduct: (ProductNewEntity) -> Unit,
    private val onClickLike: (ProductNewEntity) -> Unit,
    private val onClickAdd: (ProductNewEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductNewEntity) = with(binding) {
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