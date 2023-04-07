package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider

class CustomLongProvider(
    override val key: String?,
    override val order: Float,
    private val provide: (() -> Long?)? = null
    ) : LongPropertyProvider() {

    override fun provide(): Long? = provide?.invoke()
}