package com.affise.attribution.parameters

import com.affise.attribution.init.InitPropertiesStorage
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [AffPartParamNameTokenPropertyProvider]
 */
class AffPartParamNameTokenPropertyProviderTest {
    @Test
    fun `verify when property is init`() {
        val initProps: InitPropertiesStorage = mockk {
            every {
                getProperties()?.partParamNameToken
            } returns "token"
        }
        val provider = AffPartParamNameTokenPropertyProvider(initProps)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo("token")
        verifyAll {
            initProps.getProperties()?.partParamNameToken
        }
    }

    @Test
    fun `verify when property is not init`() {
        val initProps: InitPropertiesStorage = mockk {
            every {
                getProperties()?.partParamNameToken
            } returns null
        }
        val provider = AffPartParamNameTokenPropertyProvider(initProps)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            initProps.getProperties()?.partParamNameToken
        }
    }
}