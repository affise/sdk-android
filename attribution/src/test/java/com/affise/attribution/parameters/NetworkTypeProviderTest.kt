package com.affise.attribution.parameters

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [NetworkTypeProvider]
 */

@Suppress("DEPRECATION")
class NetworkTypeProviderTest {

    @Test
    fun `verify provide when connected to WIFI`() {
        val type = ConnectivityManager.TYPE_WIFI
        val subType = TelephonyManager.NETWORK_TYPE_UNKNOWN
        val networkInfo: android.net.NetworkInfo = mockk {
            every {
                getType()
            } returns type

            every {
                subtype
            } returns subType

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
        val provider = NetworkTypeProvider(app)
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
    fun `verify provide when connected to MOBILE with UNKNOWN subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_UNKNOWN, "")
    }

    @Test
    fun `verify provide when connected to MOBILE with EHRPD subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_EHRPD, "EHRPD")
    }

    @Test
    fun `verify provide when connected to MOBILE with GPRS subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_GPRS, "GPRS")
    }

    @Test
    fun `verify provide when connected to MOBILE with EDGE subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_EDGE, "EDGE")
    }

    @Test
    fun `verify provide when connected to MOBILE with UMTS subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_UMTS, "UMTS")
    }

    @Test
    fun `verify provide when connected to MOBILE with CDMA subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_CDMA, "CDMA")
    }

    @Test
    fun `verify provide when connected to MOBILE with EVDO_0 subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_EVDO_0, "EVDO_0")
    }

    @Test
    fun `verify provide when connected to MOBILE with EVDO_A subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_EVDO_A, "EVDO_A")
    }

    @Test
    fun `verify provide when connected to MOBILE with HSDPA subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_HSDPA, "HSDPA")
    }

    @Test
    fun `verify provide when connected to MOBILE with HSUPA subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_HSUPA, "HSUPA")
    }

    @Test
    fun `verify provide when connected to MOBILE with HSPA subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_HSPA, "HSPA")
    }

    @Test
    fun `verify provide when connected to MOBILE with IDEN subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_IDEN, "IDEN")
    }

    @Test
    fun `verify provide when connected to MOBILE with EVDO_B subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_EVDO_B, "EVDO_B")
    }

    @Test
    fun `verify provide when connected to MOBILE with LTE subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_LTE, "LTE")
    }

    @Test
    fun `verify provide when connected to MOBILE with HSPAP subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_HSPAP, "HSPAP")
    }

    @Test
    fun `verify provide when connected to MOBILE with GSM subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_GSM, "GSM")
    }

    @Test
    fun `verify provide when connected to MOBILE with TD_SCDMA subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_TD_SCDMA, "TD_SCDMA")
    }

    @Test
    fun `verify provide when connected to MOBILE with IWLAN subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_IWLAN, "IWLAN")
    }

    @Test
    fun `verify provide when connected to MOBILE with NR subtype`() {
        `test connected to mobile with subtype`(TelephonyManager.NETWORK_TYPE_NR, "NR")
    }

    @Test
    fun `verify provide when connected to UNKNOWN`() {
        val type = ConnectivityManager.TYPE_WIMAX
        val subType = TelephonyManager.NETWORK_TYPE_UNKNOWN
        val networkInfo: android.net.NetworkInfo = mockk {
            every {
                getType()
            } returns type
            every {
                subtype
            } returns subType
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
        val provider = NetworkTypeProvider(app)
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
        val provider = NetworkTypeProvider(app)
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
        val provider = NetworkTypeProvider(app)
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
        val provider = NetworkTypeProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isNull()
        app.getSystemService(Context.CONNECTIVITY_SERVICE)
        connectivityManager.activeNetworkInfo
    }

    private fun `test connected to mobile with subtype`(subType: Int, expect: String?) {
        val type = ConnectivityManager.TYPE_MOBILE
        val networkInfo: android.net.NetworkInfo = mockk {
            every {
                getType()
            } returns type
            every {
                subtype
            } returns subType

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
        val provider = NetworkTypeProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(expect)
        verifyAll {
            app.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
            networkInfo.isConnected
            networkInfo.type
            networkInfo.subtype
        }
    }
}