package com.affise.app.ui.fragments.menu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.databinding.ItemRecyclerMenuBinding
import com.affise.app.databinding.ItemRecyclerMenuDividerBinding
import javax.inject.Inject

sealed class Menu {
    data class MenuItem(val iconRes: Int, val nameRes: Int, val graph: Int) : Menu()
    object MenuDivider : Menu()
}

class AdapterMenu @Inject constructor(
) : ListAdapter<Menu, RecyclerView.ViewHolder>(MenuCallback()) {

    private var items: List<Menu> = emptyList()

    var onClick: (Menu.MenuItem) -> Unit = {}

    var current: Menu? = null
        set(value) {
            val oldValue = field
            field = value

            items.indexOf(oldValue)
                .takeIf { it >= 0 }
                ?.let {
                    notifyItemChanged(it)
                }

            items.indexOf(value)
                .takeIf { it >= 0 }
                ?.let {
                    notifyItemChanged(it)
                }
        }

    override fun submitList(list: List<Menu>?) {
        items = list ?: emptyList()

        super.submitList(list)
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        Menu.MenuDivider -> 0
        is Menu.MenuItem -> 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = LayoutInflater.from(parent.context)
        .let {
            when (viewType) {
                0 -> {
                    val binding = ItemRecyclerMenuDividerBinding.inflate(it, parent, false)
                    MenuDividerViewHolder(binding)
                }
                1 -> {
                    val binding = ItemRecyclerMenuBinding.inflate(it, parent, false)
                    MenuItemViewHolder(binding, onClick)
                }
                else -> throw NotImplementedError()
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MenuItemViewHolder) {

            val item = getItem(position) as Menu.MenuItem
            holder.bind(item, current == item)
        }
    }
}

class MenuCallback : DiffUtil.ItemCallback<Menu>() {
    override fun areItemsTheSame(
        oldItem: Menu,
        newItem: Menu
    ): Boolean = false

    override fun areContentsTheSame(
        oldItem: Menu,
        newItem: Menu
    ): Boolean = false
}

class MenuDividerViewHolder(
    binding: ItemRecyclerMenuDividerBinding
) : RecyclerView.ViewHolder(binding.root)

class MenuItemViewHolder(
    private val binding: ItemRecyclerMenuBinding,
    private val onClick: (Menu.MenuItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Menu.MenuItem, isCurrent: Boolean) = with(binding) {
        name.setText(item.nameRes)

        itemView.setOnClickListener {
            onClick.invoke(item)
        }

        icon.setImageResource(item.iconRes)

        selected.isVisible = isCurrent
    }
}