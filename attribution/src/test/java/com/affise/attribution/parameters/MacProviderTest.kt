package com.affise.attribution.parameters

import com.affise.attribution.logs.LogsManager
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test
import java.net.NetworkInterface
import java.util.*

/**
 * Test for [MacProviderTest]
 */
class MacProviderTest {

    @Test
    fun `verify provide returns null`() {
        val testMacValue = "01:02:03:04:05:06"
        val testMacValueResult = "30:31:3A:30:32:3A:30:33:3A:30:34:3A:30:35:3A:30:36"

        val nif : NetworkInterface = mockk{
            every {
                name
            } returns "wlan0"
            every {
                hardwareAddress
            } returns testMacValue.toByteArray()
        }

        mockkStatic(NetworkInterface::class) {
            every {
                NetworkInterface.getNetworkInterfaces()
            } returns Collections.enumeration(listOf(nif))

            val logsManager: LogsManager = mockk()

            val provider = MacProvider(logsManager)
            Truth.assertThat(provider.provide()).isEqualTo(testMacValueResult)

            verifyAll {
                logsManager wasNot Called
            }
        }
    }
}