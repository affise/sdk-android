package com.affise.attribution.module.advertising.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.module.advertising.oaid.OaidManager
import com.affise.attribution.parameters.ProviderType

/**
 * Provider for parameter [ProviderType.OAID]
 *
 * @property oaidManager to retrieve oaid
 */
class OaidProvider(
    private val oaidManager: OaidManager
) : StringPropertyProvider() {
    override val order: Float = 31.7f
    override val key: ProviderType = ProviderType.OAID

    override fun provide(): String? = oaidManager.getOaid()
}