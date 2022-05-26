package com.affise.app.ui.fragments.buttons.adapters

import androidx.recyclerview.widget.DiffUtil
import com.affise.attribution.events.Event

class ItemCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(
        oldItem: Event,
        newItem: Event
    ): Boolean = oldItem::class == newItem::class

    override fun areContentsTheSame(
        oldItem: Event,
        newItem: Event
    ): Boolean = true
}