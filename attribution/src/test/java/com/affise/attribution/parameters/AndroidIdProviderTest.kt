package com.affise.attribution.parameters

import android.app.Application
import android.content.ContentResolver
import android.provider.Settings
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [AndroidIdProvider]
 */
class AndroidIdProviderTest {

    @Test
    fun `verify settings secure is called with android_id argument`() {
        mockkStatic(Settings.Secure::class) {
            val secureValue = "value"
            val contentResolverMock: ContentResolver = mockk()

            every {
                Settings.Secure.getString(contentResolverMock, "android_id")
            } returns secureValue
            val app: Application = mockk {
                every {
                    contentResolver
                } returns contentResolverMock
            }

            val provider = AndroidIdProvider(app)
            val actual = provider.provide()

            Truth.assertThat(actual).isEqualTo(secureValue)

            verifyAll {
                app.contentResolver
            }
        }
    }
}