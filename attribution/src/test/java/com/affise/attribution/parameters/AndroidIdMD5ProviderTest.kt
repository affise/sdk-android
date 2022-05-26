package com.affise.attribution.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [AndroidIdMD5Provider]
 */
class AndroidIdMD5ProviderTest {

    @Test
    fun `verify android id hashed with md5 is provided when android id provider returns value`() {
        val aId = "android id"
        val aIdConverted = "android id converted"
        val aIdProvider: StringPropertyProvider = mockk {
            every {
                provide()
            } returns aId
        }
        val strToMd5Converter: Converter<String, String> = mockk {
            every {
                convert(aId)
            } returns aIdConverted
        }
        val provider = AndroidIdMD5Provider(
            aIdProvider,
            strToMd5Converter
        )
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(aIdConverted)
        verifyAll {
            aIdProvider.provide()
            strToMd5Converter.convert(aId)
        }
    }

    @Test
    fun `verify returns null when android id is null`() {
        val aIdProvider: StringPropertyProvider = mockk {
            every {
                provide()
            } returns null
        }
        val strToMd5Converter: Converter<String, String> = mockk()
        val provider = AndroidIdMD5Provider(
            aIdProvider,
            strToMd5Converter
        )
        val actual = provider.provide()
        Truth.assertThat(actual).isNull()
        verifyAll {
            aIdProvider.provide()
        }
    }
}