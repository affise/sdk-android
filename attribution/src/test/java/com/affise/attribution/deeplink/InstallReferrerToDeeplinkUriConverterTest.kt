package com.affise.attribution.deeplink

import android.net.Uri
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkConstructor
import io.mockk.unmockkStatic
import io.mockk.verifyAll
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Test for [InstallReferrerToDeeplinkUriConverter]
 */
class InstallReferrerToDeeplinkUriConverterTest {

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        mockkConstructor(Uri.Builder::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(Uri::class)
        unmockkConstructor(Uri.Builder::class)
    }

    @Test
    fun `verify relative url`() {
        val innerUri: Uri = mockk()
        val innerUriString = "deeplink://app"
        val uri: Uri = mockk {
            every { getQueryParameter("deeplink") } returns innerUriString
        }
        val uriStr = "deeplink=test"

        every {
            Uri.parse(uriStr)
        } returns uri

        every {
            Uri.parse(innerUriString)
        } returns innerUri

        every {
            anyConstructed<Uri.Builder>().encodedQuery(uriStr).build()
        } returns uri

        val converter = InstallReferrerToDeeplinkUriConverter()
        val result = converter.convert(uriStr)

        Truth.assertThat(result).isEqualTo(innerUri)

        verifyAll {
            uri.getQueryParameter("deeplink")
        }
    }

    @Test
    fun `verify absolute url`() {
        val innerUri: Uri = mockk()
        val innerUriString = "deeplink://app"

        val uri: Uri = mockk {
            every { getQueryParameter("deeplink") } returns innerUriString
        }
        val uriStr = "https://test?deeplink=test"

        every {
            Uri.parse(uriStr)
        } returns uri

        every {
            Uri.parse(innerUriString)
        } returns innerUri

        val converter = InstallReferrerToDeeplinkUriConverter()
        val result = converter.convert(uriStr)

        Truth.assertThat(result).isEqualTo(innerUri)

        verifyAll {
            uri.getQueryParameter("deeplink")
        }
    }
}