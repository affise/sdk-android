package com.affise.app.ui.fragments.details.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.R
import com.affise.app.databinding.ItemRecyclerImageBinding
import com.bumptech.glide.Glide
import javax.inject.Inject

class AdapterImages @Inject constructor(
) : ListAdapter<String, ImageViewHolder>(ImageCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder = LayoutInflater.from(parent.context)
        .let {
            ItemRecyclerImageBinding.inflate(it, parent, false)
        }
        .let {
            ImageViewHolder(it)
        }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ImageCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean = true
}

class ImageViewHolder(
    private val binding: ItemRecyclerImageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: String) = with(binding) {
        Glide.with(root)
            .load(Uri.parse("file:///android_asset/${product}"))
            .error(R.drawable.img_product_test)
            .into(root)
    }
}