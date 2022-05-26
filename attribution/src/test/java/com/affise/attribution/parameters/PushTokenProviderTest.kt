package com.affise.attribution.parameters

import android.content.SharedPreferences
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

class PushTokenProviderTest {

    @Test
    fun provideNull() {
        val preferences: SharedPreferences = mockk {
            every {
                getString(KEY_APP_PUSHTOKEN, null)
            } returns null
        }

        val provider = PushTokenProvider(preferences)
        val result = provider.provide()

        Truth.assertThat(result).isNull()

        verifyAll {
            preferences.getString(KEY_APP_PUSHTOKEN, null)
        }

    }

    @Test
    fun provide() {
        val pushtoken = "pushtoken"

        val preferences: SharedPreferences = mockk {
            every {
                getString(KEY_APP_PUSHTOKEN, null)
            } returns pushtoken
        }

        val provider = PushTokenProvider(preferences)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(pushtoken)

        verifyAll {
            preferences.getString(KEY_APP_PUSHTOKEN, null)
        }

    }

    companion object {
        private const val KEY_APP_PUSHTOKEN = "com.affise.attribution.init.PUSHTOKEN"
    }

}