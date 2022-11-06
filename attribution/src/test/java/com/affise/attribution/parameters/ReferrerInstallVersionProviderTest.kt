package com.affise.attribution.parameters

import com.affise.attribution.referrer.AffiseReferrerData
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [ReferrerInstallVersionProvider]
 */
class ReferrerInstallVersionProviderTest {
    @Test
    fun `verify when usecase returns valid date`() {
        val version = "test_version"

        val referrerData: AffiseReferrerData = mockk {
            every {
                installVersion
            } returns version
        }
        val useCase: RetrieveInstallReferrerUseCase = mockk {
            every {
                getInstallReferrer()
            } returns referrerData
        }
        val provider = ReferrerInstallVersionProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(version)
        verifyAll {
            useCase.getInstallReferrer()
            referrerData.installVersion
        }
    }

    @Test
    fun `verify when usecase returns null result is null`() {
        val useCase: RetrieveInstallReferrerUseCase = mockk {
            every {
                getInstallReferrer()
            } returns null
        }
        val provider = ReferrerInstallVersionProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getInstallReferrer()
        }
    }

    @Test
    fun `verify when usecase returns invalid object with date result is null`() {
        val version = ""

        val referrerData: AffiseReferrerData = mockk {
            every {
                installVersion
            } returns version
        }
        val useCase: RetrieveInstallReferrerUseCase = mockk {
            every {
                getInstallReferrer()
            } returns referrerData
        }
        val provider = ReferrerInstallVersionProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(version)
        verifyAll {
            useCase.getInstallReferrer()
            referrerData.installVersion
        }
    }
}