package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.utils.generateUUID

/**
 * Provider for parameter [Parameters.UUID]
 */
class UuidProvider : StringPropertyProvider() {

    override val order: Float = 64.0f
    override val key: String = Parameters.UUID

    override fun provide(): String? = generateUUID().toString()
}