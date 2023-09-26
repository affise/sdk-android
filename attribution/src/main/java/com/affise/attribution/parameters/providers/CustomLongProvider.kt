package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider

class CustomLongProvider(
    override val key: ProviderType,
    override val order: Float,
    private val provide: (() -> Long?)? = null
    ) : LongPropertyProvider() {

    override fun provide(): Long? = provide?.invoke()
}