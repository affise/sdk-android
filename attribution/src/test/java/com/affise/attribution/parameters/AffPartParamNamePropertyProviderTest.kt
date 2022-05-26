package com.affise.attribution.parameters

import com.affise.attribution.init.InitPropertiesStorage
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll

import org.junit.Test

/**
 * Test for [AffPartParamNamePropertyProvider]
 */
class AffPartParamNamePropertyProviderTest {

    @Test
    fun `verify when property is init`() {
        val initProps: InitPropertiesStorage = mockk {
            every {
                getProperties()?.partParamName
            } returns "token"
        }
        val provider = AffPartParamNamePropertyProvider(initProps)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo("token")
        verifyAll {
            initProps.getProperties()?.partParamName
        }
    }

    @Test
    fun `verify when property is not init`() {
        val initProps: InitPropertiesStorage = mockk {
            every {
                getProperties()?.partParamName
            } returns null
        }
        val provider = AffPartParamNamePropertyProvider(initProps)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            initProps.getProperties()?.partParamName
        }
    }
}