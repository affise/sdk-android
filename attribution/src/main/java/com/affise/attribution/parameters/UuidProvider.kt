package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.*

/**
 * Provider for parameter [Parameters.UUID]
 */
class UuidProvider : StringPropertyProvider() {

    override fun provide(): String? = UUID.randomUUID()?.toString()
}