package com.affise.app.ui.fragments.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.databinding.ItemRecyclerColorBinding
import com.affise.app.entity.Colors
import javax.inject.Inject

data class ColorItem(val color: Colors, val enabled: Boolean)

class AdapterColors @Inject constructor(
) : ListAdapter<ColorItem, ColorViewHolder>(ColorCallback()) {

    val onSelected: (ColorItem) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColorViewHolder = LayoutInflater.from(parent.context)
        .let {
            ItemRecyclerColorBinding.inflate(it, parent, false)
        }
        .let {
            ColorViewHolder(it)
        }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(getItem(position), onSelected)
    }
}

class ColorCallback : DiffUtil.ItemCallback<ColorItem>() {
    override fun areItemsTheSame(
        oldItem: ColorItem,
        newItem: ColorItem
    ): Boolean = oldItem.color == newItem.color

    override fun areContentsTheSame(
        oldItem: ColorItem,
        newItem: ColorItem
    ): Boolean = oldItem.enabled == newItem.enabled
}

class ColorViewHolder(
    private val binding: ItemRecyclerColorBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(color: ColorItem, onSelected: (ColorItem) -> Unit) = with(binding) {
        root.setOnClickListener {
            onSelected.invoke(color)
        }

        root.isEnabled = color.enabled

        root.alpha = if (color.enabled) 1f else 0.3f

        image.setImageResource(color.color.colorRes)
    }
}