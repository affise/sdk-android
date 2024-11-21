package com.affise.attribution.parameters

import com.affise.attribution.parameters.providers.ReferrerClickTimestampProvider
import com.affise.attribution.referrer.AffiseReferrerData
import com.affise.attribution.usecase.StoreInstallReferrerUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [ReferrerClickTimestampProvider]
 */
class ReferrerClickTimestampProviderTest {
    @Test
    fun `verify when usecase returns valid date`() {
        val time = 1635176812345L

        val referrerData: AffiseReferrerData = mockk {
            every {
                referrerClickTimestampSeconds
            } returns time
        }
        val useCase: StoreInstallReferrerUseCase = mockk {
            every {
                getInstallReferrerData()
            } returns referrerData
        }
        val provider = ReferrerClickTimestampProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(time)
        verifyAll {
            useCase.getInstallReferrerData()
            referrerData.referrerClickTimestampSeconds
        }
    }

    @Test
    fun `verify when usecase returns null result is null`() {
        val useCase: StoreInstallReferrerUseCase = mockk {
            every {
                getInstallReferrerData()
            } returns null
        }
        val provider = ReferrerClickTimestampProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getInstallReferrerData()
        }
    }

    @Test
    fun `verify when usecase returns invalid object with date result is null`() {
        val time = 0L

        val referrerData: AffiseReferrerData = mockk {
            every {
                referrerClickTimestampSeconds
            } returns time
        }
        val useCase: StoreInstallReferrerUseCase = mockk {
            every {
                getInstallReferrerData()
            } returns referrerData
        }
        val provider = ReferrerClickTimestampProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getInstallReferrerData()
            referrerData.referrerClickTimestampSeconds
        }
    }
}