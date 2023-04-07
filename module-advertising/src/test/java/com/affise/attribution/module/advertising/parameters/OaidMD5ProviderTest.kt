package com.affise.attribution.module.advertising.parameters

import com.affise.attribution.converter.Converter
import com.affise.attribution.module.advertising.oaid.OaidManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

class OaidMD5ProviderTest {

    @Test
    fun provideNull() {
        val oaidManager: OaidManager = mockk {
            every {
                getOaid()
            } returns null
        }

        val md5Converter: Converter<String, String> = mockk()

        val provider = OaidMD5Provider(oaidManager, md5Converter)
        val result = provider.provide()

        Truth.assertThat(result).isNull()

        verifyAll {
            oaidManager.getOaid()
        }
    }

    @Test
    fun provide() {
        val oaid = "oaid"
        val oaidMd5 = "oaidMd5"

        val oaidManager: OaidManager = mockk {
            every {
                getOaid()
            } returns oaid
        }

        val md5Converter: Converter<String, String> = mockk {
            every {
                convert(oaid)
            } returns oaidMd5
        }

        val provider = OaidMD5Provider(oaidManager, md5Converter)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(oaidMd5)

        verifyAll {
            oaidManager.getOaid()
            md5Converter.convert(oaid)
        }
    }

}