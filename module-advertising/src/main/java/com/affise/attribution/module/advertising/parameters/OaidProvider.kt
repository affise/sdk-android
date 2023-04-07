package com.affise.attribution.module.advertising.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.module.advertising.oaid.OaidManager

/**
 * Provider for parameter [Parameters.OAID]
 *
 * @property oaidManager to retrieve oaid
 */
class OaidProvider(
    private val oaidManager: OaidManager
) : StringPropertyProvider() {
    override val order: Float = 31.5f
    override val key: String = Parameters.OAID

    override fun provide(): String? = oaidManager.getOaid()
}