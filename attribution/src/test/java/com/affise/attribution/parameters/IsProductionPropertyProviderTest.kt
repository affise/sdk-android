package com.affise.attribution.parameters

import com.affise.attribution.init.InitPropertiesStorage
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [IsProductionPropertyProvider]
 */
class IsProductionPropertyProviderTest {

    @Test
    fun `verify when isProduction is false`() {
        val initProps: InitPropertiesStorage = mockk {
            every {
                getProperties()?.isProduction
            } returns false
        }
        val provider = IsProductionPropertyProvider(initProps)
        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(TYPE_SANDBOX)

        verifyAll {
            initProps.getProperties()?.isProduction
        }
    }

    @Test
    fun `verify when isProduction is true`() {
        val initProps: InitPropertiesStorage = mockk {
            every {
                getProperties()?.isProduction
            } returns true
        }
        val provider = IsProductionPropertyProvider(initProps)
        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(TYPE_PRODUCTION)

        verifyAll {
            initProps.getProperties()?.isProduction
        }
    }

    companion object {
        const val TYPE_SANDBOX = "Sandbox"
        const val TYPE_PRODUCTION = "Production"
    }
}