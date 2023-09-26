package com.affise.attribution.module.phone.parameters


import android.content.Context
import android.telephony.TelephonyManager
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.ISP]
 *
 * @property app to retrieve system service
 */
class IspNameProvider(
    private val app: Context
) : StringPropertyProvider() {
    override val order: Float = 37.1f
    override val key: ProviderType = ProviderType.ISP

    override fun provide(): String? = (app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager)
        ?.simOperatorName
        ?.ifEmpty { null }
}