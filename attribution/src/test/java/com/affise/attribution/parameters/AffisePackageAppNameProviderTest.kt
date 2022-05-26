package com.affise.attribution.parameters

import android.content.Context
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [AffisePackageAppNameProvider]
 */
class AffisePackageAppNameProviderTest {

    @Test
    fun `verify provide`() {
        val name = "com.targetApp.packageName"

        val context: Context = mockk {
            every {
                packageName
            } returns name
        }
        val provider = AffisePackageAppNameProvider(context)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(name)
        verifyAll {
            context.packageName
        }
    }

    @Test
    fun `verify provide when getPackageName returns null`() {
        val context: Context = mockk {
            every {
                packageName
            } returns null
        }
        val provider = AffisePackageAppNameProvider(context)
        val actual = provider.provide()
        Truth.assertThat(actual).isNull()
        verifyAll {
            context.packageName
        }
    }
}