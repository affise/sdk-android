package com.affise.app.ui.fragments.details.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.R
import com.affise.app.databinding.ItemRecyclerSizeBinding
import javax.inject.Inject

enum class SizeType {
    EURO,
    US,
    ASIA
}

data class Size(val sizeUs: Double, val sizeEuro: Double, val sizeAsia: Double) {
    companion object {
        fun getAllSize(): List<Size> = listOf(
            Size(6.0, 38.0, 24.0),
            Size(6.5, 38.5, 24.5),
            Size(7.0, 39.0, 25.0),
            Size(7.5, 40.0, 25.5),
            Size(8.0, 41.0, 26.0),
            Size(8.5, 42.0, 26.5),
            Size(9.0, 43.0, 27.0),
            Size(9.5, 43.5, 27.5),
            Size(10.0, 44.0, 28.0),
            Size(10.5, 44.5, 28.5),
            Size(11.0, 45.0, 29.0),
            Size(11.5, 45.5, 29.5),
            Size(12.0, 46.0, 30.0),
        )
    }
}

@SuppressLint("NotifyDataSetChanged")
class AdapterSize @Inject constructor(
) : ListAdapter<Size, SizeViewHolder>(SizeCallback()) {

    var currentType: SizeType = SizeType.US
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    var enabledSize: List<Size> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    var currentSize: Size = Size(8.0, 41.0, 26.0)
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SizeViewHolder = LayoutInflater.from(parent.context)
        .let {
            ItemRecyclerSizeBinding.inflate(it, parent, false)
        }
        .let {
            SizeViewHolder(it)
        }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val size = getItem(position)
        holder.bind(size, currentType, size in enabledSize, size == currentSize)
    }
}

class SizeCallback : DiffUtil.ItemCallback<Size>() {
    override fun areItemsTheSame(
        oldItem: Size,
        newItem: Size
    ): Boolean = oldItem.sizeUs == newItem.sizeUs

    override fun areContentsTheSame(
        oldItem: Size,
        newItem: Size
    ): Boolean = true
}

class SizeViewHolder(
    private val binding: ItemRecyclerSizeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(size: Size, type: SizeType, enabled: Boolean, isCurrent: Boolean) = with(binding) {
        current.isInvisible = !isCurrent

        value.text = when (type) {
            SizeType.EURO -> size.sizeEuro
            SizeType.US -> size.sizeUs
            SizeType.ASIA -> size.sizeAsia
        }.toString()

        val textAppearance = if (enabled) R.style.EnabledSize else R.style.DisabledSize

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            value.setTextAppearance(textAppearance)
        } else {
            @Suppress("DEPRECATION")
            value.setTextAppearance(itemView.context, textAppearance)
        }
    }
}