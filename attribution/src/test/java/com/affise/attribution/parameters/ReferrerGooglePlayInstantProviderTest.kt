package com.affise.attribution.parameters

import com.affise.attribution.parameters.providers.ReferrerGooglePlayInstantProvider
import com.affise.attribution.referrer.AffiseReferrerData
import com.affise.attribution.usecase.StoreInstallReferrerUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [ReferrerGooglePlayInstantProvider]
 */
class ReferrerGooglePlayInstantProviderTest {
    @Test
    fun `verify when usecase returns valid date`() {
        val googlePlayInstant = true

        val referrerData: AffiseReferrerData = mockk {
            every {
                googlePlayInstantParam
            } returns googlePlayInstant
        }
        val useCase: StoreInstallReferrerUseCase = mockk {
            every {
                getInstallReferrerData()
            } returns referrerData
        }
        val provider = ReferrerGooglePlayInstantProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(googlePlayInstant)
        verifyAll {
            useCase.getInstallReferrerData()
            referrerData.googlePlayInstantParam
        }
    }

    @Test
    fun `verify when usecase returns null result is null`() {
        val useCase: StoreInstallReferrerUseCase = mockk {
            every {
                getInstallReferrerData()
            } returns null
        }
        val provider = ReferrerGooglePlayInstantProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getInstallReferrerData()
        }
    }

    @Test
    fun `verify when usecase returns invalid object with date result is null`() {
        val googlePlayInstant = false

        val referrerData: AffiseReferrerData = mockk {
            every {
                googlePlayInstantParam
            } returns googlePlayInstant
        }
        val useCase: StoreInstallReferrerUseCase = mockk {
            every {
                getInstallReferrerData()
            } returns referrerData
        }
        val provider = ReferrerGooglePlayInstantProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(googlePlayInstant)
        verifyAll {
            useCase.getInstallReferrerData()
            referrerData.googlePlayInstantParam
        }
    }
}