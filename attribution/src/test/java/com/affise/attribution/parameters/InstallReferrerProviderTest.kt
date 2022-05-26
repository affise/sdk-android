package com.affise.attribution.parameters

import android.app.Application
import com.affise.attribution.referrer.AffiseReferrerData
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [InstallReferrerProvider]
 */
class InstallReferrerProviderTest {

    private val app: Application = mockk()

    @Test
    fun `verify when usecase returns null result is null`() {
        val useCase: RetrieveInstallReferrerUseCase = mockk {
            every {
                getInstallReferrer()
            } returns null
        }
        val provider = InstallReferrerProvider(app, useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getInstallReferrer()
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
        val useCase: RetrieveInstallReferrerUseCase = mockk {
            every {
                getInstallReferrer()
            } returns referrerData
        }
        val provider = InstallReferrerProvider(app, useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(referrer)
        verifyAll {
            useCase.getInstallReferrer()
            referrerData.installReferrer
        }
    }
}