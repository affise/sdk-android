package com.affise.attribution.parameters

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [MNCProvider]
 */
class MNCProviderTest {

    @Test
    fun `verify provide`() {
        val code = 100
        val configuration: Configuration = Configuration().apply {
            mnc = code
        }
        val resources: Resources = mockk {
            every {
                getConfiguration()
            } returns configuration
        }
        val app: Application = mockk {
            every {
                getResources()
            } returns resources
        }
        val provider = MNCProvider(app)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(code.toString())
        verifyAll {
            app.resources
            resources.configuration
        }
    }
}