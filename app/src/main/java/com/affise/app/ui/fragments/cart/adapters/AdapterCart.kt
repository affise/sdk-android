package com.affise.app.ui.fragments.cart.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.R
import com.affise.app.databinding.ItemRecyclerCartBinding
import com.affise.app.entity.ProductEntity
import com.bumptech.glide.Glide
import javax.inject.Inject

data class ProductCartEntity(
    val product: ProductEntity,
    var count: Int
)

class AdapterCart @Inject constructor(
) : ListAdapter<ProductCartEntity, ProductCartViewHolder>(ProductCartCallback()) {

    var onClickProduct: (ProductCartEntity) -> Unit = {}

    var onClickPlus: (ProductCartEntity) -> Unit = {}

    var onClickMinus: (ProductCartEntity) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductCartViewHolder = LayoutInflater.from(parent.context)
        .let {
            ItemRecyclerCartBinding.inflate(it, parent, false)
        }
        .let {
            ProductCartViewHolder(it, onClickProduct, onClickPlus, onClickMinus)
        }

    override fun onBindViewHolder(holder: ProductCartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductCartCallback : DiffUtil.ItemCallback<ProductCartEntity>() {
    override fun areItemsTheSame(
        oldItem: ProductCartEntity,
        newItem: ProductCartEntity
    ): Boolean = oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(
        oldItem: ProductCartEntity,
        newItem: ProductCartEntity
    ): Boolean = oldItem.product.name == newItem.product.name &&
        oldItem.product.price == newItem.product.price &&
        oldItem.product.unit == newItem.product.unit &&
        oldItem.product.rating == newItem.product.rating &&
        oldItem.count == newItem.count
}

class ProductCartViewHolder(
    private val binding: ItemRecyclerCartBinding,
    private val onClickProduct: (ProductCartEntity) -> Unit,
    private val onClickPlus: (ProductCartEntity) -> Unit,
    private val onClickMinus: (ProductCartEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductCartEntity) = with(binding) {
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

        count.text = product.count.toString()

        main.setOnClickListener {
            onClickProduct.invoke(product)
        }

        plus.setOnClickListener {
            onClickPlus.invoke(product)
        }

        minus.setOnClickListener {
            onClickMinus.invoke(product)
        }
    }
}