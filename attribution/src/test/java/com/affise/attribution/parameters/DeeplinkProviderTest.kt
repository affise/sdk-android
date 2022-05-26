package com.affise.attribution.parameters

import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [DeeplinkProvider]
 */
class DeeplinkProviderTest {

    @Test
    fun `verify provide returns empty string when repository returns null`() {
        val repository: DeeplinkClickRepository = mockk {
            every { getDeeplink() } returns null
        }
        val provider = DeeplinkProvider(repository)
        Truth.assertThat(provider.provide()).isEqualTo("")

        verifyAll {
            repository.getDeeplink()
        }
    }

    @Test
    fun `verify provide returns string when repository returns string`() {
        val repository: DeeplinkClickRepository = mockk {
            every { getDeeplink() } returns "string"
        }
        val provider = DeeplinkProvider(repository)
        Truth.assertThat(provider.provide()).isEqualTo( "string")

        verifyAll {
            repository.getDeeplink()
        }
    }
}