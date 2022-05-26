package com.affise.attribution.converter

import android.util.Base64
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verifyAll
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Test for [ConverterToBase64]
 */
class ConverterToBase64Test {

    private val value = "value"
    private val valueByteArray = value.toByteArray()
    private val valueBase64 = "valueBase64"

    @Before
    fun setup() {
        mockkStatic(Base64::class)
        every {
            Base64.encodeToString(valueByteArray, Base64.NO_WRAP)
        } returns valueBase64
    }

    @After
    fun tearDown() {
        unmockkStatic(Base64::class)
    }

    @Test
    fun convert() {
        val converter = ConverterToBase64()
        val result = converter.convert(value)

        Truth.assertThat(result).isEqualTo(valueBase64)

        verifyAll {
            Base64.encodeToString(valueByteArray, Base64.NO_WRAP)
        }
    }
}