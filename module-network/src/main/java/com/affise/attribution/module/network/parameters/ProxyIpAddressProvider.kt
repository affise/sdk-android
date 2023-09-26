package com.affise.attribution.module.network.parameters

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.PROXY_IP_ADDRESS]
 *
 * @property context to retrieve ConnectivityManager
 * @property buildConfigPropertiesProvider to get current SDK_INT
 */
class ProxyIpAddressProvider(
    private val context: Context,
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {
    override val order: Float = 24.1f
    override val key: ProviderType = ProviderType.PROXY_IP_ADDRESS

    @SuppressLint("NewApi")
    override fun provide(
    ) = if (buildConfigPropertiesProvider.getSDKVersion() >= Build.VERSION_CODES.M) {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.defaultProxy?.host
    } else {
        null
    }
}