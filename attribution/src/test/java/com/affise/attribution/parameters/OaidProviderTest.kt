package com.affise.attribution.parameters

import com.affise.attribution.oaid.OaidManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

class OaidProviderTest {

    @Test
    fun provideNull() {
        val oaidManager: OaidManager = mockk {
            every {
                getOaid()
            } returns null
        }

        val provider = OaidProvider(oaidManager)
        val result = provider.provide()

        Truth.assertThat(result).isNull()

        verifyAll {
            oaidManager.getOaid()
        }
    }

    @Test
    fun provide() {
        val oaid = "oaid"

        val oaidManager: OaidManager = mockk {
            every {
                getOaid()
            } returns oaid
        }

        val provider = OaidProvider(oaidManager)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(oaid)

        verifyAll {
            oaidManager.getOaid()
        }
    }

}