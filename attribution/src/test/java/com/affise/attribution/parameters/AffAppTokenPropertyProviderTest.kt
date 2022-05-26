package com.affise.attribution.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.init.InitPropertiesStorage
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [AffAppTokenPropertyProvider]
 */
class AffAppTokenPropertyProviderTest {

    @Test
    fun `verify when property is init`() {
        val initProps: InitPropertiesStorage = mockk {
            every {
                getProperties()?.affiseAppId
            } returns "affiseAppId"
            every {
                getProperties()?.secretId
            } returns "secretId"
        }

        val converter: Converter<String, String> = mockk {
            every {
                convert("affiseAppId0secretId")
            } returns "tokenSHA256"
        }

        val provider = AffAppTokenPropertyProvider(initProps, converter)
        val actual = provider.provideWithParam("0")

        Truth.assertThat(actual).isEqualTo("tokenSHA256")

        verifyAll {
            initProps.getProperties()?.affiseAppId
            initProps.getProperties()?.secretId
            converter.convert("affiseAppId0secretId")
        }
    }
}