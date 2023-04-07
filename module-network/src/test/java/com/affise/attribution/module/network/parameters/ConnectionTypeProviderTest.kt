package com.affise.attribution.module.network.parameters

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [ConnectionTypeProvider]
 */
@Suppress("DEPRECATION")
class ConnectionTypeProviderTest {

    @Test
    fun `verify provide when connected to WIFI`() {
        val type = ConnectivityManager.TYPE_WIFI
        val networkInfo: android.net.NetworkInfo = mockk {
            every {
                getType()
            } returns type

            every {
                isConnected
            } returns true
        }
        val connectivityManager: ConnectivityManager = mockk {
            every {
                activeNetworkInfo
            } returns networkInfo
        }
        val app: Application = mockk {
            every {
                getSystemService(ConnectivityManager::class.java)
            } returns connectivityManager
            every {
                getSystemService(Context.CONNECTIVITY_SERVICE)
            } returns connectivityManager
        }
        val provider = ConnectionTypeProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo("WIFI")
        verifyAll {
            app.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
            networkInfo.isConnected
            networkInfo.type
        }
    }

    @Test
    fun `verify provide when connected to MOBILE`() {
        val type = ConnectivityManager.TYPE_MOBILE
        val networkInfo: android.net.NetworkInfo = mockk {
            every {
                getType()
            } returns type

            every {
                isConnected
            } returns true
        }
        val connectivityManager: ConnectivityManager = mockk {
            every {
                activeNetworkInfo
            } returns networkInfo
        }
        val app: Application = mockk {
            every {
                getSystemService(ConnectivityManager::class.java)
            } returns connectivityManager
            every {
                getSystemService(Context.CONNECTIVITY_SERVICE)
            } returns connectivityManager
        }
        val provider = ConnectionTypeProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo("MOBILE")
        verifyAll {
            app.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
            networkInfo.isConnected
            networkInfo.type
        }
    }

    @Test
    fun `verify provide when connected to null`() {
        val type = ConnectivityManager.TYPE_WIMAX
        val networkInfo: android.net.NetworkInfo = mockk {
            every {
                getType()
            } returns type

            every {
                isConnected
            } returns true
        }
        val connectivityManager: ConnectivityManager = mockk {
            every {
                activeNetworkInfo
            } returns networkInfo
        }
        val app: Application = mockk {
            every {
                getSystemService(ConnectivityManager::class.java)
            } returns connectivityManager
            every {
                getSystemService(Context.CONNECTIVITY_SERVICE)
            } returns connectivityManager
        }
        val provider = ConnectionTypeProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isNull()
        verifyAll {
            app.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
            networkInfo.isConnected
            networkInfo.type
        }
    }

    @Test
    fun `verify provide when not connected`() {
        val networkInfo: android.net.NetworkInfo = mockk {
            every {
                isConnected
            } returns false
        }
        val connectivityManager: ConnectivityManager = mockk {
            every {
                activeNetworkInfo
            } returns networkInfo
        }
        val app: Application = mockk {
            every {
                getSystemService(ConnectivityManager::class.java)
            } returns connectivityManager
            every {
                getSystemService(Context.CONNECTIVITY_SERVICE)
            } returns connectivityManager
        }
        val provider = ConnectionTypeProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isNull()
        verifyAll {
            app.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
            networkInfo.isConnected
        }
    }

    @Test
    fun `verify provide when system service is null`() {
        val app: Application = mockk {
            every {
                getSystemService(ConnectivityManager::class.java)
            } returns null
            every {
                getSystemService(Context.CONNECTIVITY_SERVICE)
            } returns null
        }
        val provider = ConnectionTypeProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isNull()
        verifyAll {
            app.getSystemService(Context.CONNECTIVITY_SERVICE)
        }
    }

    @Test
    fun `verify provide when activeNetworkInfo is null`() {
        val connectivityManager: ConnectivityManager = mockk {
            every {
                activeNetworkInfo
            } returns null
        }

        val app: Application = mockk {
            every {
                getSystemService(ConnectivityManager::class.java)
            } returns connectivityManager
            every {
                getSystemService(Context.CONNECTIVITY_SERVICE)
            } returns connectivityManager
        }
        val provider = ConnectionTypeProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isNull()
        verifyAll {
            app.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
        }
    }
}