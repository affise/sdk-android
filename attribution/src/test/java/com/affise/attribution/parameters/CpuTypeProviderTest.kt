package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [CpuTypeProvider]
 */
class CpuTypeProviderTest {

    @Test
    fun `verify provide`() {
        val propsProvider: BuildConfigPropertiesProvider = mockk {
            every {
                getSupportedABIs()
            } returns listOf("a", "b")
        }
        val provider = CpuTypeProvider(propsProvider)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo("a, b")
        verifyAll {
            propsProvider.getSupportedABIs()
        }
    }
}