package com.affise.attribution.module.network.parameters

import android.content.Context
import android.net.ConnectivityManager
import android.net.ProxyInfo
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [ProxyIpAddressProvider]
 */
class ProxyIpAddressProviderTest {

    @Test
    fun `verify provide returns host when proxy info is not null and api verison greater or equal 23`() {
        val hostMock = "host"
        val buildConfigPropertiesProvider: BuildConfigPropertiesProvider = mockk {
            every { getSDKVersion() } returns 23
        }
        val proxy: ProxyInfo = mockk {
            every { host } returns hostMock
        }
        val connectivityManager: ConnectivityManager = mockk {
            every { defaultProxy } returns proxy
        }
        val context: Context = mockk {
            every { getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        }

        val provider = ProxyIpAddressProvider(context, buildConfigPropertiesProvider)

        Truth.assertThat(provider.provide()).isEqualTo(hostMock)

        verifyAll {
            buildConfigPropertiesProvider.getSDKVersion()
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.defaultProxy
            proxy.host
        }

    }

    @Test
    fun `verify provide returns null when proxy info is null and api verison greater or equal 23`() {
        val buildConfigPropertiesProvider: BuildConfigPropertiesProvider = mockk {
            every { getSDKVersion() } returns 23
        }
        val proxy: ProxyInfo = mockk {
            every { host } returns null
        }
        val connectivityManager: ConnectivityManager = mockk {
            every { defaultProxy } returns proxy
        }
        val context: Context = mockk {
            every { getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        }

        val provider = ProxyIpAddressProvider(context, buildConfigPropertiesProvider)

        Truth.assertThat(provider.provide()).isNull()

        verifyAll {
            buildConfigPropertiesProvider.getSDKVersion()
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.defaultProxy
            proxy.host
        }

    }

    @Test
    fun `verify provide returns null when defaultProxy is null and api verison greater or equal 23`() {
        val buildConfigPropertiesProvider: BuildConfigPropertiesProvider = mockk {
            every { getSDKVersion() } returns 23
        }

        val connectivityManager: ConnectivityManager = mockk {
            every { defaultProxy } returns null
        }
        val context: Context = mockk {
            every { getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        }

        val provider = ProxyIpAddressProvider(context, buildConfigPropertiesProvider)

        Truth.assertThat(provider.provide()).isNull()

        verifyAll {
            buildConfigPropertiesProvider.getSDKVersion()
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.defaultProxy
        }

    }

    @Test
    fun `verify provide returns null when connectivity manager is null and api verison greater or equal 23`() {
        val buildConfigPropertiesProvider: BuildConfigPropertiesProvider = mockk {
            every { getSDKVersion() } returns 23
        }

        val context: Context = mockk {
            every { getSystemService(Context.CONNECTIVITY_SERVICE) } returns null
        }

        val provider = ProxyIpAddressProvider(context, buildConfigPropertiesProvider)

        Truth.assertThat(provider.provide()).isNull()

        verifyAll {
            buildConfigPropertiesProvider.getSDKVersion()
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
        }

    }

    @Test
    fun `verify provide returns null when  api verison less than 23`() {
        val buildConfigPropertiesProvider: BuildConfigPropertiesProvider = mockk {
            every { getSDKVersion() } returns 22
        }

        val context: Context = mockk()

        val provider = ProxyIpAddressProvider(context, buildConfigPropertiesProvider)

        Truth.assertThat(provider.provide()).isNull()

        verifyAll {
            buildConfigPropertiesProvider.getSDKVersion()
        }

    }
}