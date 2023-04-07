package com.affise.attribution.module.phone.parameters

import android.content.Context
import android.telephony.TelephonyManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [IspNameProvider]
 */
class IspNameProviderTest {

    @Test
    fun `verify provide returns sim operator empty`() {
        val operatorName = ""
        val telephonyManager: TelephonyManager = mockk {
            every {
                simOperatorName
            } returns operatorName
        }
        val context: Context = mockk {
            every {
                getSystemService("phone")
            } returns telephonyManager
        }
        val provider = IspNameProvider(context)
        val actual = provider.provide()

        Truth.assertThat(actual).isNull()

        verifyAll {
            context.getSystemService("phone")
            telephonyManager.simOperatorName
        }
    }

    @Test
    fun `verify provide returns sim operator name`() {
        val operatorName = "operator"
        val telephonyManager: TelephonyManager = mockk {
            every {
                simOperatorName
            } returns operatorName
        }
        val context: Context = mockk {
            every {
                getSystemService("phone")
            } returns telephonyManager
        }
        val provider = IspNameProvider(context)
        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(operatorName)

        verifyAll {
            context.getSystemService("phone")
            telephonyManager.simOperatorName
        }
    }

    @Test
    fun `verify provide returns null if system service returned null`() {
        val context: Context = mockk {
            every {
                getSystemService("phone")
            } returns null
        }
        val provider = IspNameProvider(context)
        val actual = provider.provide()

        Truth.assertThat(actual).isNull()

        verifyAll {
            context.getSystemService("phone")
        }
    }

    @Test
    fun `verify provide returns null if telephonyManager returns null`() {
        val telephonyManager: TelephonyManager = mockk {
            every {
                simOperatorName
            } returns null
        }
        val context: Context = mockk {
            every {
                getSystemService("phone")
            } returns telephonyManager
        }
        val provider = IspNameProvider(context)
        val actual = provider.provide()

        Truth.assertThat(actual).isNull()

        verifyAll {
            context.getSystemService("phone")
            telephonyManager.simOperatorName
        }
    }
}