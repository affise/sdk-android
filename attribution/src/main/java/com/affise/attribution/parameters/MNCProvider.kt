package com.affise.attribution.parameters

import android.app.Application
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.MNCODE]
 *
 * @property app manager to fetch resources from
 */
class MNCProvider(
    private val app: Application
) : StringPropertyProvider() {

    override fun provide(): String = app.resources.configuration.mnc.toString()
}