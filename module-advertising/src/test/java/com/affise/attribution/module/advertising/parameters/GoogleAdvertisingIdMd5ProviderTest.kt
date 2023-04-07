package com.affise.attribution.module.advertising.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.module.advertising.advertising.AdvertisingIdManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [GoogleAdvertisingIdMd5Provider]
 */
class GoogleAdvertisingIdMd5ProviderTest {

    @Test
    fun `verify provide when id is returned`() {
        val id = "adid"
        val idMd5 = "adidmd5"
        val manager: AdvertisingIdManager = mockk {
            every {
                getAdvertisingId()
            } returns id
        }
        val converter: Converter<String, String> = mockk {
            every {
                convert(id)
            } returns idMd5
        }
        val provider = GoogleAdvertisingIdMd5Provider(manager, converter)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(idMd5)
        verifyAll {
            manager.getAdvertisingId()
            converter.convert(id)
        }
    }

    @Test
    fun `verify provide when id is null returned`() {
        val manager: AdvertisingIdManager = mockk {
            every {
                getAdvertisingId()
            } returns null
        }
        val converter: Converter<String, String> = mockk()
        val provider = GoogleAdvertisingIdMd5Provider(manager, converter)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            manager.getAdvertisingId()
        }
    }
}