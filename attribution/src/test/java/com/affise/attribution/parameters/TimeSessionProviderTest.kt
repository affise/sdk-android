package com.affise.attribution.parameters

import com.affise.attribution.session.SessionManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

class TimeSessionProviderTest {

    @Test
    fun provide() {
        val sessionTime = 10L

        val sessionManager: SessionManager = mockk {
            every {
                getSessionTime()
            } returns sessionTime
        }

        val provider = TimeSessionProvider(sessionManager)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(sessionTime)

        verifyAll {
            sessionManager.getSessionTime()
        }

    }

}