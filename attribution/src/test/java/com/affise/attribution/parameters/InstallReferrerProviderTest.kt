package com.affise.attribution.parameters

import android.app.Application
import com.affise.attribution.parameters.providers.InstallReferrerProvider
import com.affise.attribution.referrer.AffiseReferrerData
import com.affise.attribution.usecase.StoreInstallReferrerUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Ignore
import org.junit.Test

/**
 * Test for [InstallReferrerProvider]
 */
@Ignore
class InstallReferrerProviderTest {

    private val app: Application = mockk()

    @Test
    fun `verify when usecase returns null result is null`() {
        val useCase: StoreInstallReferrerUseCase = mockk {
            every {
                getInstallReferrerData()
            } returns null
        }
        val provider = InstallReferrerProvider(app, useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getInstallReferrerData()
        }
    }

    @Test
    fun `verify when usecase returns referrer`() {
        val referrer = "organic"

        val referrerData: AffiseReferrerData = mockk {
            every {
                installReferrer
            } returns referrer
        }
        val useCase: StoreInstallReferrerUseCase = mockk {
            every {
                getInstallReferrerData()
            } returns referrerData
        }
        val provider = InstallReferrerProvider(app, useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(referrer)
        verifyAll {
            useCase.getInstallReferrerData()
            referrerData.installReferrer
        }
    }
}