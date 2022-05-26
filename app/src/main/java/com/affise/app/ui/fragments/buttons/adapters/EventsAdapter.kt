package com.affise.app.ui.fragments.buttons.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.affise.app.databinding.ListItemEventBinding
import com.affise.attribution.events.Event

class EventsAdapter(
    itemCallback: DiffUtil.ItemCallback<Event>,
    private val onClick: (Event) -> Unit
) : ListAdapter<Event, EventViewHolder>(itemCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): EventViewHolder = LayoutInflater.from(parent.context)
        .let {
            ListItemEventBinding.inflate(it, parent, false)
        }
        .let {
            EventViewHolder(it, onClick)
        }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}