package com.affise.app.ui.adapters

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.IntRange
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.affise.app.extensions.convertDpToPixels

class SpaceDecoration(
    private val paddingTop: Float = 0f,
    private val paddingEnd: Float = 0f,
    private val paddingBottom: Float = 0f,
    private val paddingStart: Float = 0f,
) : RecyclerView.ItemDecoration() {

    private lateinit var appearance: Appearance

    override fun getItemOffsets(
        outRect: Rect,
        itemView: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        ensureAppearance(parent.context)

        parent.findContainingViewHolder(itemView)?.let { viewHolder ->
            val position = viewHolder.adapterPosition
            val lastPosition = parent.adapter?.itemCount?.let { it - 1 }

            val extraPaddingTop = if (position != 0) appearance.paddingTop else 0
            val extraPaddingStart = if (position != 0) appearance.paddingStart else 0
            val extraPaddingBottom = if (position != lastPosition) appearance.paddingBottom else 0
            val extraPaddingEnd = if (position != lastPosition) appearance.paddingEnd else 0

            outRect.set(extraPaddingStart, extraPaddingTop, extraPaddingEnd, extraPaddingBottom)
        }
    }

    private fun ensureAppearance(context: Context) {
        if (!this::appearance.isInitialized) {
            appearance = Appearance(
                paddingTop = context.convertDpToPixels(paddingTop),
                paddingEnd = context.convertDpToPixels(paddingEnd),
                paddingBottom = context.convertDpToPixels(paddingBottom),
                paddingStart = context.convertDpToPixels(paddingStart),
            )
        }
    }

    private class Appearance(
        @param:IntRange(from = 0) @param:Px @field:IntRange(from = 0) @field:Px val paddingTop: Int,
        @param:IntRange(from = 0) @param:Px @field:IntRange(from = 0) @field:Px val paddingEnd: Int,
        @param:IntRange(from = 0) @param:Px @field:IntRange(from = 0) @field:Px val paddingBottom: Int,
        @param:IntRange(from = 0) @param:Px @field:IntRange(from = 0) @field:Px val paddingStart: Int
    )
}