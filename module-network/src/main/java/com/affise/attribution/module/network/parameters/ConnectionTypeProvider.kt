package com.affise.attribution.module.network.parameters

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provides connection type [ProviderType.CONNECTION_TYPE]
 *
 * @property app app context
 */
class ConnectionTypeProvider(
    private val app: Application
) : StringPropertyProvider() {
    override val order: Float = 21.1f
    override val key: ProviderType = ProviderType.CONNECTION_TYPE

    override fun provide(): String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        app.getSystemService(ConnectivityManager::class.java)
    } else {
        app.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    }?.let { connectivityManager ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork
                ?.let { connectivityManager.getNetworkCapabilities(it) }
                ?.let {
                    when {
                        it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WIFI"
                        it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "MOBILE"
                        else -> null
                    }
                }
        } else {
            getTypeBelowMarshmallow(connectivityManager)
        }
    }

    @Suppress("DEPRECATION")
    private fun getTypeBelowMarshmallow(
        connectivityManager: ConnectivityManager
    ): String? = connectivityManager.activeNetworkInfo
        ?.takeIf { it.isConnected }
        ?.let {
            when (it.type) {
                ConnectivityManager.TYPE_WIFI -> "WIFI"
                ConnectivityManager.TYPE_MOBILE -> "MOBILE"
                else -> null
            }
        }
}