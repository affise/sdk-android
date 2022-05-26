package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [HardwareNameProvider]
 */
class HardwareNameProviderTest {

    @Test
    fun `verify provide`() {
        val hardware = "hw"
        val propertiesProvider: BuildConfigPropertiesProvider = mockk {
            every {
                getHardware()
            } returns hardware
        }
        val provider = HardwareNameProvider(propertiesProvider)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(hardware)
        verifyAll {
            propertiesProvider.getHardware()
        }
    }
}