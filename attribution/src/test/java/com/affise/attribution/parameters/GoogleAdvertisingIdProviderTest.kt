package com.affise.attribution.parameters

import com.affise.attribution.advertising.AdvertisingIdManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [GoogleAdvertisingIdProvider]
 */
class GoogleAdvertisingIdProviderTest {

    @Test
    fun `verify when manager returns advertising id`() {
        val adId = "id"
        val manager: AdvertisingIdManager = mockk {
            every {
                getAdvertisingId()
            } returns adId
        }
        val provider = GoogleAdvertisingIdProvider(manager)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(adId)
        verifyAll {
            manager.getAdvertisingId()
        }
    }

    @Test
    fun `verify when manager does not return advertising id`() {
        val manager: AdvertisingIdManager = mockk {
            every {
                getAdvertisingId()
            } returns null
        }
        val provider = GoogleAdvertisingIdProvider(manager)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(provider.provide())
        verifyAll {
            manager.getAdvertisingId()
        }
    }
}