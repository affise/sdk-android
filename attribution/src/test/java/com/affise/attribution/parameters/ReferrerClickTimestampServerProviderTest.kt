package com.affise.attribution.parameters

import com.affise.attribution.referrer.AffiseReferrerData
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [ReferrerClickTimestampServerProvider]
 */
class ReferrerClickTimestampServerProviderTest {
    @Test
    fun `verify when usecase returns valid date`() {
        val time = 1635176812345L

        val referrerData: AffiseReferrerData = mockk {
            every {
                referrerClickTimestampServerSeconds
            } returns time
        }
        val useCase: RetrieveInstallReferrerUseCase = mockk {
            every {
                getInstallReferrer()
            } returns referrerData
        }
        val provider = ReferrerClickTimestampServerProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(time)
        verifyAll {
            useCase.getInstallReferrer()
            referrerData.referrerClickTimestampServerSeconds
        }
    }

    @Test
    fun `verify when usecase returns null result is null`() {
        val useCase: RetrieveInstallReferrerUseCase = mockk {
            every {
                getInstallReferrer()
            } returns null
        }
        val provider = ReferrerClickTimestampServerProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getInstallReferrer()
        }
    }

    @Test
    fun `verify when usecase returns invalid object with date result is null`() {
        val time = 0L

        val referrerData: AffiseReferrerData = mockk {
            every {
                referrerClickTimestampServerSeconds
            } returns time
        }
        val useCase: RetrieveInstallReferrerUseCase = mockk {
            every {
                getInstallReferrer()
            } returns referrerData
        }
        val provider = ReferrerClickTimestampServerProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getInstallReferrer()
            referrerData.referrerClickTimestampServerSeconds
        }
    }
}