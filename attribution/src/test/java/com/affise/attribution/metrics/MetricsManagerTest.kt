package com.affise.attribution.metrics

import android.app.Activity
import android.content.res.Resources
import android.view.View
import com.affise.attribution.converter.StringToSHA1Converter
import com.affise.attribution.utils.ActivityActionsManager
import com.affise.attribution.utils.ActivityClickCallback
import com.affise.attribution.utils.ActivityLifecycleCallback
import com.affise.attribution.utils.timestamp
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Test for [MetricsManagerImpl]
 */
class MetricsManagerTest {

    private val activityName = "Activity"

    private val viewId = 1

    private val viewResourceEntryName = "viewId"

    private val activityNameViewIdSha1 = "activityNameViewIdSha1"

    private val eventName = "AutoCatchingClickEvent_activityNameViewIdSha1"

    private val activity: Activity = mockk()

    private val resourcesMockk: Resources = mockk {
        every { getResourceEntryName(viewId) } returns viewResourceEntryName
    }

    private val view: View = mockk {
        every { id } returns viewId
        every { resources } returns resourcesMockk
    }

    private val startedListeners = slot<ActivityLifecycleCallback>()
    private val stoppedListeners = slot<ActivityLifecycleCallback>()
    private val clickListeners = slot<ActivityClickCallback>()

    private val activityActionsManager: ActivityActionsManager = mockk {
        every { addOnActivityStartedListener(capture(startedListeners)) } just Runs
        every { addOnActivityStoppedListener(capture(stoppedListeners)) } just Runs
        every { addOnActivityClickListener(capture(clickListeners)) } just Runs
    }

    private val metricsUseCase: MetricsUseCase = mockk {
        every { addOpenActivityTime(activityName, any()) } just Runs
        every { addClickOnActivity(activityName, any()) } just Runs
    }

    private val converterToSHA1: StringToSHA1Converter = mockk {
        every { convert(activityName + viewResourceEntryName) } returns activityNameViewIdSha1
    }

    private val metricsManager = MetricsManagerImpl(activityActionsManager, metricsUseCase, converterToSHA1)

    @Before
    fun setUp() {
        mockkConstructor(StringToSHA1Converter::class)
        every {
            constructedWith<StringToSHA1Converter>().convert(activityName + viewResourceEntryName)
        } returns "eventName"
    }

    @After
    fun tearDown() {
        unmockkConstructor(StringToSHA1Converter::class)
    }

    @Test
    fun `init object`() {
        Truth.assertThat(startedListeners.isCaptured).isTrue()
        Truth.assertThat(stoppedListeners.isCaptured).isTrue()
        Truth.assertThat(clickListeners.isCaptured).isTrue()

        verifyAll {
            activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
            activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
            activityActionsManager.addOnActivityClickListener(capture(clickListeners))
        }
    }

    @Test
    fun `init true`() {
        metricsManager.setEnabledMetrics(true)

        mockkStatic(::timestamp) {
            every {
                timestamp()
            } returns 0

            startedListeners.captured.handle(activity)

            every {
                timestamp()
            } returns 100

            stoppedListeners.captured.handle(activity)

            verifyAll {
                activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
                activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
                activityActionsManager.addOnActivityClickListener(capture(clickListeners))
                metricsUseCase.addOpenActivityTime(activityName, 100)
            }
        }
    }

    @Test
    fun `init false`() {
        metricsManager.setEnabledMetrics(false)

        mockkStatic(::timestamp) {
            every {
                timestamp()
            } returns 0

            startedListeners.captured.handle(activity)

            every {
                timestamp()
            } returns 100

            stoppedListeners.captured.handle(activity)

            verifyAll {
                activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
                activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
                activityActionsManager.addOnActivityClickListener(capture(clickListeners))
                metricsUseCase wasNot Called
            }
        }
    }

    @Test
    fun `init true stop with out start`() {
        metricsManager.setEnabledMetrics(true)

        mockkStatic(::timestamp) {
            every {
                timestamp()
            } returns 100

            stoppedListeners.captured.handle(activity)

            verifyAll {
                activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
                activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
                activityActionsManager.addOnActivityClickListener(capture(clickListeners))
                metricsUseCase wasNot Called
            }
        }
    }

    @Test
    fun `init true second stop with out second start`() {
        metricsManager.setEnabledMetrics(true)

        mockkStatic(::timestamp) {
            every {
                timestamp()
            } returns 0

            startedListeners.captured.handle(activity)

            every {
                timestamp()
            } returns 100

            stoppedListeners.captured.handle(activity)

            every {
                timestamp()
            } returns 1000

            stoppedListeners.captured.handle(activity)

            verifyAll {
                activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
                activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
                activityActionsManager.addOnActivityClickListener(capture(clickListeners))
                metricsUseCase.addOpenActivityTime(activityName, 100)
            }
        }
    }

    @Test
    fun `init true click`() {
        metricsManager.setEnabledMetrics(true)

        clickListeners.captured.handle(activity, view)

        verifyAll {
            activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
            activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
            activityActionsManager.addOnActivityClickListener(capture(clickListeners))
            metricsUseCase.addClickOnActivity(activityName, eventName)
        }
    }

    @Test
    fun `init false click`() {
        metricsManager.setEnabledMetrics(false)

        clickListeners.captured.handle(activity, view)

        verifyAll {
            activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
            activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
            activityActionsManager.addOnActivityClickListener(capture(clickListeners))
            metricsUseCase wasNot Called
        }
    }

    @Test
    fun enabledMetrics() {
        metricsManager.setEnabledMetrics(false)

        metricsManager.setEnabledMetrics(true)

        clickListeners.captured.handle(activity, view)

        verifyAll {
            activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
            activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
            activityActionsManager.addOnActivityClickListener(capture(clickListeners))
            metricsUseCase.addClickOnActivity(activityName, eventName)
        }
    }

    @Test
    fun disabledMetrics() {
        metricsManager.setEnabledMetrics(false)

        metricsManager.setEnabledMetrics(false)

        clickListeners.captured.handle(activity, view)

        verifyAll {
            activityActionsManager.addOnActivityStartedListener(capture(startedListeners))
            activityActionsManager.addOnActivityStoppedListener(capture(stoppedListeners))
            activityActionsManager.addOnActivityClickListener(capture(clickListeners))
            metricsUseCase wasNot Called
        }
    }
}