package com.affise.attribution.parameters

import android.app.Application
import android.app.UiModeManager
import android.content.res.Configuration
import android.content.res.Resources
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [DeviceTypeProvider]
 */
class DeviceTypeProviderTest {

    @Test
    fun `provider returns tv if UiModeManager configuration is television`() {
        val app: Application = mockk()
        val provider = DeviceTypeProvider(app)

        val uiModeManager: UiModeManager = mockk {
            every {
                currentModeType
            } returns Configuration.UI_MODE_TYPE_TELEVISION
        }
        every {
            app.getSystemService(SYSTEM_SERVICE)
        } returns uiModeManager
        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(TYPE_TV)

        verifyAll {
            app.getSystemService(SYSTEM_SERVICE)
            uiModeManager.currentModeType
        }
    }

    @Test
    fun `provider returns car if UiModeManager configuration is car`() {
        val app: Application = mockk()
        val provider = DeviceTypeProvider(app)

        val uiModeManager: UiModeManager = mockk {
            every {
                currentModeType
            } returns Configuration.UI_MODE_TYPE_CAR
        }
        every {
            app.getSystemService(SYSTEM_SERVICE)
        } returns uiModeManager
        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(TYPE_CAR)

        verifyAll {
            app.getSystemService(SYSTEM_SERVICE)
            uiModeManager.currentModeType
        }
    }

    @Test
    fun `provider returns phone if UiModeManager is not present and screen size is SMALL`() {
        `test device type provider when UiModeManager is not present`(
            size = Configuration.SCREENLAYOUT_SIZE_SMALL,
            expectedType = TYPE_SMART
        )
    }

    @Test
    fun `provider returns tablet if UiModeManager is not present and screen size is LARGE`() {
        `test device type provider when UiModeManager is not present`(
            size = Configuration.SCREENLAYOUT_SIZE_LARGE,
            expectedType = TYPE_TABLET
        )
    }

    @Test
    fun `provider returns tablet if UiModeManager is not present and screen size is XLARGE`() {
        `test device type provider when UiModeManager is not present`(
            size = Configuration.SCREENLAYOUT_SIZE_XLARGE,
            expectedType = TYPE_TABLET
        )
    }

    @Test
    fun `provider returns phone if UiModeManager is not present and screen size is NORMAL`() {
        `test device type provider when UiModeManager is not present`(
            size = Configuration.SCREENLAYOUT_SIZE_NORMAL,
            expectedType = TYPE_SMART
        )
    }

    @Test
    fun `provider returns phone if UiModeManager is not present and screen size is UNDEFINED`() {
        `test device type provider when UiModeManager is not present`(
            size = Configuration.SCREENLAYOUT_SIZE_UNDEFINED,
            expectedType = TYPE_SMART
        )
    }

    private fun `test device type provider when UiModeManager is not present`(size: Int, expectedType: String) {
        val app: Application = mockk()
        val provider = DeviceTypeProvider(app)

        every {
            app.getSystemService(SYSTEM_SERVICE)
        } returns null

        val configurationMock: Configuration = Configuration().apply {
            screenLayout = size
        }
        val resources: Resources = mockk {
            every {
                configuration
            } returns configurationMock
        }
        every {
            app.resources
        } returns resources
        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(expectedType)

        verifyAll {
            app.getSystemService(SYSTEM_SERVICE)
            app.resources
            resources.configuration
        }
    }

    companion object {
        const val SYSTEM_SERVICE = "uimode"
        const val TYPE_SMART = "smartphone"
        const val TYPE_TABLET = "tablet"
        const val TYPE_TV = "tv"
        const val TYPE_CAR = "car"
    }
}