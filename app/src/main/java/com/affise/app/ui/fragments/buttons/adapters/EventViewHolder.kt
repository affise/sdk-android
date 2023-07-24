package com.affise.app.ui.fragments.buttons.adapters

import androidx.recyclerview.widget.RecyclerView
import com.affise.app.databinding.ListItemEventBinding
import com.affise.attribution.events.Event
import com.affise.attribution.events.subscription.*

class EventViewHolder(
    private val binding: ListItemEventBinding,
    private val onClick: (Event) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Event) {
        when (item) {
            is BaseSubscriptionEvent -> bind(item, item.subtype.typeName)
            else -> bind(item, item.getName())
        }
    }

    private fun bind(item: Event, title: String) {
        binding.eventButton.setOnClickListener {
            onClick.invoke(item)
        }

        binding.eventButton.text = title
    }
}