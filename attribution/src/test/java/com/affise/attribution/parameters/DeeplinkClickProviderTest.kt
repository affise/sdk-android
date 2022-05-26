package com.affise.attribution.parameters

import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll

import org.junit.Test

/**
 * Test for [DeeplinkClickPropertyProvider]
 */
class DeeplinkClickProviderTest {

    @Test
    fun `verify when isDeeplink is false`() {
        val repository: DeeplinkClickRepository = mockk {
            every {
                isDeeplinkClick()
            } returns false
        }
        val provider = DeeplinkClickPropertyProvider(repository)
        val actual = provider.provide()
        Truth.assertThat(actual).isFalse()
        verifyAll {
            repository.isDeeplinkClick()
        }
    }

    @Test
    fun `verify when isDeeplink is true`() {
        val repository: DeeplinkClickRepository = mockk {
            every {
                isDeeplinkClick()
            } returns true
        }
        val provider = DeeplinkClickPropertyProvider(repository)
        val actual = provider.provide()
        Truth.assertThat(actual).isTrue()
        verifyAll {
            repository.isDeeplinkClick()
        }
    }
}