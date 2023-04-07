package com.affise.attribution.module.network.parameters

import com.affise.attribution.converter.StringToSHA1Converter
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [MacSha1Provider]
 */
class MacSha1ProviderTest {

    @Test
    fun `verify provide when mac is not null`() {
        val mac = "0F:XX"
        val macProvider: MacProvider = mockk {
            every {
                provide()
            } returns mac
        }
        val sha = "0FSHA"
        val converter: StringToSHA1Converter = mockk {
            every {
                convert(mac)
            } returns sha
        }
        val provider = MacSha1Provider(macProvider, converter)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(sha)
        verifyAll {
            macProvider.provide()
            converter.convert(mac)
        }
    }

    @Test
    fun `verify provide when mac null`() {
        val macProvider: MacProvider = mockk {
            every {
                provide()
            } returns null
        }
        val converter: StringToSHA1Converter = mockk()
        val provider = MacSha1Provider(macProvider, converter)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            macProvider.provide()
        }
    }
}