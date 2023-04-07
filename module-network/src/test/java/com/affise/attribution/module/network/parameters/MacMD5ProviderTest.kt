package com.affise.attribution.module.network.parameters

import com.affise.attribution.converter.Converter
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [MacMD5Provider]
 */
class MacMD5ProviderTest {

    @Test
    fun `verify provide when mac is not null`() {
        val mac = "0X:EE"
        val macProvider: MacProvider = mockk {
            every {
                provide()
            } returns mac
        }
        val md5str = "md5"
        val md5Converter: Converter<String, String> = mockk {
            every {
                convert(mac)
            } returns md5str
        }
        val provider = MacMD5Provider(macProvider, md5Converter)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(md5str)
        verifyAll {
            macProvider.provide()
            md5Converter.convert(mac)
        }
    }

    @Test
    fun `verify provide when mac is null`() {
        val macProvider: MacProvider = mockk {
            every {
                provide()
            } returns null
        }
        val md5Converter: Converter<String, String> = mockk()
        val provider = MacMD5Provider(macProvider, md5Converter)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            macProvider.provide()
        }
    }
}