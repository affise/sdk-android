package com.affise.attribution.deeplink

import android.app.Activity
import android.net.Uri
import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.utils.ActivityActionsManager
import com.affise.attribution.utils.ActivityLifecycleCallback
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test

/**
 * Test for [DeeplinkManagerImpl]
 */
class DeeplinkManagerImplTest {

    @Test
    fun `verify onInit activity lifecycle callback is not added if init is called multiple times`() {
        val initProperties: InitPropertiesStorage = mockk()
        val isDeeplinkRepository: DeeplinkClickRepository = mockk()

        val listeners = slot<ActivityLifecycleCallback>()
        val activityActionsManager: ActivityActionsManager = mockk {
            every {
                addOnActivityResumedListener(capture(listeners))
            } just Runs
        }

        val manager = DeeplinkManagerImpl(
            initProperties, isDeeplinkRepository, activityActionsManager
        )

        manager.init()
        manager.init()

        Truth.assertThat(listeners.isCaptured).isTrue()

        verifyAll {
            activityActionsManager.addOnActivityResumedListener(capture(listeners))
        }
    }

    @Test
    fun `verify onInit lifecycle callback invokes deeplink callback on activity resume`() {
        val initProperties: InitPropertiesStorage = mockk {
            every {
                getProperties()?.affiseAppId
            } returns "appid"
        }
        val isDeeplinkRepository: DeeplinkClickRepository = mockk {
            every {
                setDeeplinkClick(true)
            } just Runs

            every {
                setDeeplink("/appid")
            } just Runs
        }

        val uri: Uri = mockk {
            every {
                path
            } returns "/appid"
            every {
                this@mockk.toString()
            } returns "/appid"
        }
        val deeplinkCallback: OnDeeplinkCallback = mockk {
            every {
                handleDeeplink(uri)
            } returns true
        }

        val listeners = slot<ActivityLifecycleCallback>()
        val activityActionsManager: ActivityActionsManager = mockk {
            every {
                addOnActivityResumedListener(capture(listeners))
            } just Runs
        }

        val manager = DeeplinkManagerImpl(
            initProperties, isDeeplinkRepository, activityActionsManager
        )

        manager.init()
        manager.setDeeplinkCallback(deeplinkCallback)

        val activity: Activity = mockk {
            every {
                intent
            } returns mockk {
                every {
                    data
                } returns uri

                every {
                    setData(null)
                } returns this
            }
        }

        listeners.captured.handle(activity)

        verifyAll {
            deeplinkCallback.handleDeeplink(uri)
        }
    }
}