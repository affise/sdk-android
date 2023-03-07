package com.affise.attribution.parameters

import android.content.SharedPreferences
import com.affise.attribution.utils.generateUUID
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verifyAll
import org.junit.After
import org.junit.Test

import org.junit.Before
import java.util.UUID

/**
 * Test for [RefTokenProvider]
 */
class RefTokenProviderTest {

    @Before
    fun setUp() {
        mockkStatic(::generateUUID)
    }

    @After
    fun tearDown() {
        unmockkStatic(::generateUUID)
    }

    @Test
    fun `verify provide() when shared prefs is empty new UUID should be generated and stored to prefs`() {
        val spKey = "com.affise.attribution.parameters.REFTOKEN"
        val generatedUUID = "00000000-0000-0000-0000-000000000000"
        every {
            generateUUID()
        } returns UUID(0, 0)

        val editor: SharedPreferences.Editor = mockk {
            every {
                putString(spKey, generatedUUID)
            } returns this
            every {
                commit()
            } returns true
        }
        val sp: SharedPreferences = mockk {
            every {
                getString(spKey, null)
            } returns null
            every {
                edit()
            } returns editor
        }

        val provider = RefTokenProvider(sp)

        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(generatedUUID)
        verifyAll {
            sp.getString(spKey, null)
            sp.edit()
            editor.putString(spKey, generatedUUID)
            editor.commit()
        }
    }

    @Test
    fun `verify provide() when shared prefs contains UUID return UUID`() {
        val spKey = "com.affise.attribution.parameters.REFTOKEN"
        val generatedUUID = "00000000-0000-0000-0000-000000000000"

        val sp: SharedPreferences = mockk {
            every {
                getString(spKey, null)
            } returns generatedUUID
        }

        val provider = RefTokenProvider(sp)

        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(generatedUUID)
        verifyAll {
            sp.getString(spKey, null)
        }
    }
}