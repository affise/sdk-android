package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

/**
 * Test for [ApiLevelOSProvider]
 */
class ApiLevelOSProviderTest {

    @Test
    fun provide() {
        val propertiesProvider: BuildConfigPropertiesProvider = mockk {
            every {
                getSDKVersion()
            } returns 42
        }

        val provider = ApiLevelOSProvider(propertiesProvider)

        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo("42")
    }
}