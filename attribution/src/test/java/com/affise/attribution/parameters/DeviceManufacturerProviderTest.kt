package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [DeviceManufacturerProvider]
 */
class DeviceManufacturerProviderTest {

    @Test
    fun `verify when manufacturer is returned from build config`() {
        val manufacturer = "xiaomi"
        val propertiesProvider: BuildConfigPropertiesProvider = mockk {
            every {
                getManufacturer()
            } returns manufacturer
        }
        val provider = DeviceManufacturerProvider(propertiesProvider)

        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(manufacturer)
        verifyAll {
            propertiesProvider.getManufacturer()
        }
    }

    @Test
    fun `verify when manufacturer returned from build config is null`() {
        val propertiesProvider: BuildConfigPropertiesProvider = mockk {
            every {
                getManufacturer()
            } returns null
        }
        val provider = DeviceManufacturerProvider(propertiesProvider)

        val actual = provider.provide()
        Truth.assertThat(actual).isNull()
        verifyAll {
            propertiesProvider.getManufacturer()
        }
    }
}