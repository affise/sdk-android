package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [OSVersionProvider]
 */
class OSVersionProviderTest {

    @Test
    fun `verify provider returns release name from build config`() {
        val buildConfig: BuildConfigPropertiesProvider = mockk {
            every {
                getReleaseName()
            } returns "2.2"
        }

        val provider = OSVersionProvider(buildConfig)

        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo("2.2")

        verifyAll {
            buildConfig.getReleaseName()
        }

    }

    @Test
    fun `verify provider returns null if release name from build config is null`() {
        val buildConfig: BuildConfigPropertiesProvider = mockk {
            every {
                getReleaseName()
            } returns null
        }

        val provider = OSVersionProvider(buildConfig)

        val actual = provider.provide()

        Truth.assertThat(actual).isNull()

        verifyAll {
            buildConfig.getReleaseName()
        }

    }
}