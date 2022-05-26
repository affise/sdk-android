package com.affise.app.ui.fragments.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.R
import com.affise.app.databinding.ItemRecyclerPageBinding
import com.affise.app.extensions.convertDpToPixels
import javax.inject.Inject

class ImagePagesItem(val name: String, var isSelected: Boolean = false)

class AdapterImagePages @Inject constructor(
) : ListAdapter<ImagePagesItem, ImagePageViewHolder>(PageCallback()) {

    private var selection = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagePageViewHolder = LayoutInflater.from(parent.context)
        .let {
            ItemRecyclerPageBinding.inflate(it, parent, false)
        }
        .let {
            ImagePageViewHolder(it)
        }

    override fun onBindViewHolder(holder: ImagePageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getSelectedPosition(): Int = selection

    fun setSelected(position: Int) {
        if (selection == position) return

        if (selection in 0 until itemCount) {
            getItem(selection)?.isSelected = false

            notifyItemChanged(selection)
        }

        if (position in 0 until itemCount) {
            getItem(position)?.isSelected = true

            notifyItemChanged(position)
        }

        selection = position
    }
}

class PageCallback : DiffUtil.ItemCallback<ImagePagesItem>() {
    override fun areItemsTheSame(
        oldItem: ImagePagesItem,
        newItem: ImagePagesItem
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: ImagePagesItem,
        newItem: ImagePagesItem
    ): Boolean = oldItem.isSelected == newItem.isSelected
}

class ImagePageViewHolder(
    private val binding: ItemRecyclerPageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ImagePagesItem) = with(binding) {
        root.setCardBackgroundColor(
            ContextCompat.getColor(
                root.context,
                if (item.isSelected) R.color.ebony_clay else R.color.gray_c4
            )
        )

        root.layoutParams = root.layoutParams.apply {
            width = root.context.convertDpToPixels(
                if (item.isSelected) 26f else 10f
            )
        }
    }
}