package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider

class EmptyStringProvider(
    override val key: String?,
    override val order: Float,
) : StringPropertyProvider() {
    override fun provide(): String = ""
}