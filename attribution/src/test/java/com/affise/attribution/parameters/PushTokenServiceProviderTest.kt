package com.affise.attribution.parameters

import com.affise.attribution.parameters.providers.PushTokenServiceProvider
import com.affise.attribution.settings.PushTokenService
import com.affise.attribution.usecase.PushTokenUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

class PushTokenServiceProviderTest {

    @Test
    fun provideNull() {
        val useCase: PushTokenUseCase = mockk {
            every {
                getPushTokenService()
            } returns null
        }

        val provider = PushTokenServiceProvider(useCase)
        val result = provider.provide()

        Truth.assertThat(result).isNull()

        verifyAll {
            useCase.getPushTokenService()
        }
    }

    @Test
    fun provide() {
        val serviceName = PushTokenService.FIREBASE.service

        val useCase: PushTokenUseCase = mockk {
            every {
                getPushTokenService()
            } returns serviceName
        }

        val provider = PushTokenServiceProvider(useCase)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(serviceName)

        verifyAll {
            useCase.getPushTokenService()
        }
    }
}