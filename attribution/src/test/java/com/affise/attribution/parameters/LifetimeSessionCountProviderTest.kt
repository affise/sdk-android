package com.affise.attribution.parameters

import com.affise.attribution.session.SessionManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

class LifetimeSessionCountProviderTest {

    @Test
    fun provide() {
        val lifetimeSessionTime = 10L

        val sessionManager: SessionManager = mockk {
            every {
                getLifetimeSessionTime()
            } returns lifetimeSessionTime
        }

        val provider = LifetimeSessionCountProvider(sessionManager)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(lifetimeSessionTime)

        verifyAll {
            sessionManager.getLifetimeSessionTime()
        }

    }
}