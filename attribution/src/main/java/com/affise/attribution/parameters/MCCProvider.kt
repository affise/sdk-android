package com.affise.attribution.parameters

import android.app.Application
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.MCCODE]
 *
 * @property app manager to fetch resources from
 */
class MCCProvider(
    private val app: Application
) : StringPropertyProvider() {

    override fun provide(): String = app.resources.configuration.mcc.toString()
}