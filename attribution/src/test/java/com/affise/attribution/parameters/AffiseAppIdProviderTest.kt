package com.affise.attribution.parameters

import com.affise.attribution.init.AffiseInitProperties
import com.affise.attribution.init.InitPropertiesStorage
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [AffiseAppIdProvider]K
 */
class AffiseAppIdProviderTest {

    @Test
    fun `verify provide`() {
        val appId = "test"
        val props: AffiseInitProperties = mockk {
            every {
                affiseAppId
            } returns appId
        }
        val storage: InitPropertiesStorage = mockk {
            every {
                getProperties()
            } returns props
        }
        val provider = AffiseAppIdProvider(storage)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(appId)
        verifyAll {
            storage.getProperties()
            props.affiseAppId
        }
    }

    @Test
    fun `verify provide when properties storage returns null`() {
        val storage: InitPropertiesStorage = mockk {
            every {
                getProperties()
            } returns null
        }
        val provider = AffiseAppIdProvider(storage)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            storage.getProperties()
        }
    }
}