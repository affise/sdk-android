package com.affise.attribution.session

import android.content.SharedPreferences
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test
import java.util.*

class SessionManagerImplTest {

    private val keyLifetimeSessionCount = "lifetime_session_count"
    private val keyAffiseSessionCount = "affise_session_count"

    private val saveLifetimeSessionCount = 10000L
    private val saveSessionCount = 10L

    private val timeStart = 0L
    private val timeStartSession = 15001L
    private val timeNotStartSession = 14999L
    private val timeHideSession = 1000L

    private val calendarStart: Calendar = mockk {
        every { timeInMillis } returns timeStart
    }

    private val calendarStartSession: Calendar = mockk {
        every { timeInMillis } returns timeStartSession
    }

    private val calendarNotStartSession: Calendar = mockk {
        every { timeInMillis } returns timeNotStartSession
    }

    private val calendarHideSession: Calendar = mockk {
        every { timeInMillis } returns timeHideSession
    }

    private lateinit var listenerTest: (count: Long) -> Unit

    private val provider = object : CurrentActiveActivityCountProvider {
        override fun init() {
        }

        override fun addActivityCountListener(listener: (count: Long) -> Unit) {
            listenerTest = listener
        }

        override fun getActivityCount(): Long {
            return 0
        }
    }

    @Test
    fun getSaveSessionTime() {
        val provider: CurrentActiveActivityCountProvider = mockk()
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns saveLifetimeSessionCount
        }

        val manager = SessionManagerImpl(preferences, provider)
        val lifetimeSessionTime = manager.getLifetimeSessionTime()

        Truth.assertThat(lifetimeSessionTime).isEqualTo(saveLifetimeSessionCount)

        verifyAll {
            preferences.getLong(keyLifetimeSessionCount, 0L)
        }

    }

    /**
     * less 15 seconds return only save time
     */
    @Test
    fun getLifetimeSessionTimeNotStartSession() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns saveLifetimeSessionCount
        }

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendarStart

            val manager = SessionManagerImpl(preferences, provider).apply { init() }

            listenerTest(1)

            every {
                Calendar.getInstance()
            } returns calendarNotStartSession

            val lifetimeSessionTime = manager.getLifetimeSessionTime()

            Truth.assertThat(lifetimeSessionTime).isEqualTo(saveLifetimeSessionCount + timeNotStartSession)

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                calendarStart.timeInMillis
                calendarNotStartSession.timeInMillis
            }

        }
    }

    /**
     * more 15 seconds return save time and time current session
     */
    @Test
    fun getStartSessionTime() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns saveLifetimeSessionCount
        }

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendarStart

            val manager = SessionManagerImpl(preferences, provider).apply { init() }

            listenerTest(1)

            every {
                Calendar.getInstance()
            } returns calendarStartSession

            val lifetimeSessionTime = manager.getLifetimeSessionTime()

            Truth.assertThat(lifetimeSessionTime).isEqualTo(saveLifetimeSessionCount + timeStartSession)

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                calendarStart.timeInMillis
                calendarStartSession.timeInMillis
            }

        }
    }

    /**
     * After hide app only save session
     */
    @Test
    fun getStartSessionAndHideTime() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns saveLifetimeSessionCount

            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns 0

            every {
                edit().putLong(keyLifetimeSessionCount, any()).commit()
            } returns true

            every {
                edit().putLong(keyAffiseSessionCount, 1).commit()
            } returns true
        }

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendarStart

            val manager = SessionManagerImpl(preferences, provider).apply { init() }

            listenerTest(1)

            every {
                Calendar.getInstance()
            } returns calendarStartSession

            listenerTest(0)

            every {
                Calendar.getInstance()
            } returns calendarHideSession

            val lifetimeSessionTime = manager.getLifetimeSessionTime()

            Truth.assertThat(lifetimeSessionTime).isEqualTo(saveLifetimeSessionCount)

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
                preferences.edit().putLong(keyLifetimeSessionCount, any()).commit()
                preferences.edit().putLong(keyAffiseSessionCount, 1).commit()
                calendarStart.timeInMillis
                calendarStartSession.timeInMillis
            }

        }
    }

    //--SessionTime---

    @Test
    fun getSessionTimeNotOpenActive() {
        val preferences: SharedPreferences = mockk()

        val manager = SessionManagerImpl(preferences, provider).apply { init() }

        val sessionTime = manager.getSessionTime()

        Truth.assertThat(sessionTime).isEqualTo(0L)

    }

    @Test
    fun getSessionTimeNotStartSession() {
        val preferences: SharedPreferences = mockk()

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendarStart

            val manager = SessionManagerImpl(preferences, provider).apply { init() }

            listenerTest(1)

            every {
                Calendar.getInstance()
            } returns calendarNotStartSession

            val sessionTime = manager.getSessionTime()

            Truth.assertThat(sessionTime).isEqualTo(timeNotStartSession)

            verifyAll {
                calendarStart.timeInMillis
                calendarNotStartSession.timeInMillis
            }
        }
    }

    @Test
    fun getSessionTimeActive() {
        val preferences: SharedPreferences = mockk()

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendarStart

            val manager = SessionManagerImpl(preferences, provider).apply { init() }

            listenerTest(1)

            every {
                Calendar.getInstance()
            } returns calendarStartSession

            val sessionTime = manager.getSessionTime()

            Truth.assertThat(sessionTime).isEqualTo(timeStartSession)

            verifyAll {
                calendarStart.timeInMillis
                calendarStartSession.timeInMillis
            }
        }
    }

    @Test
    fun isSessionActive() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns saveSessionCount

            every {
                edit().putLong(keyAffiseSessionCount, any()).commit()
            } returns true
        }

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendarStart

            val manager = SessionManagerImpl(preferences, provider).apply { init() }

            listenerTest(1)

            every {
                Calendar.getInstance()
            } returns calendarStartSession

            val isSessionActive = manager.isSessionActive()

            Truth.assertThat(isSessionActive).isTrue()

            verifyAll {
                preferences.getLong(keyAffiseSessionCount, 0L)
                preferences.edit().putLong(keyAffiseSessionCount, any()).commit()
                calendarStart.timeInMillis
                calendarStartSession.timeInMillis
            }

        }
    }

    @Test
    fun isSessionNotActive() {
        val preferences: SharedPreferences = mockk()

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendarStart

            val manager = SessionManagerImpl(preferences, provider).apply { init() }

            listenerTest(1)

            every {
                Calendar.getInstance()
            } returns calendarNotStartSession

            val isSessionActive = manager.isSessionActive()

            Truth.assertThat(isSessionActive).isFalse()

            verifyAll {
                calendarStart.timeInMillis
                calendarNotStartSession.timeInMillis
            }

        }
    }

    @Test
    fun isSessionClose() {
        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendarStart

            val preferences: SharedPreferences = mockk {
                every {
                    getLong(keyLifetimeSessionCount, 0L)
                } returns saveSessionCount

                every {
                    getLong(keyAffiseSessionCount, 0L)
                } returns 0

                every {
                    edit().putLong(keyLifetimeSessionCount, any()).commit()
                } returns true

                every {
                    edit().putLong(keyAffiseSessionCount, 1).commit()
                } returns true
            }

            val manager = SessionManagerImpl(preferences, provider).apply { init() }

            listenerTest(1)

            every {
                Calendar.getInstance()
            } returns calendarStartSession

            listenerTest(0)

            every {
                Calendar.getInstance()
            } returns calendarHideSession

            val isSessionActive = manager.isSessionActive()

            Truth.assertThat(isSessionActive).isFalse()

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
                preferences.edit().putLong(keyLifetimeSessionCount, any()).commit()
                preferences.edit().putLong(keyAffiseSessionCount, 1).commit()
                calendarStart.timeInMillis
                calendarStartSession.timeInMillis
            }

        }
    }

    @Test
    fun getSessionCount() {
        val preferences: SharedPreferences = mockk {
            every {
                edit().putLong(keyAffiseSessionCount, any()).commit()
            } returns true

            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns saveSessionCount
        }

        val manager = SessionManagerImpl(preferences, provider).apply { init() }

        val isSessionActive = manager.getSessionCount()

        Truth.assertThat(isSessionActive).isEqualTo(saveSessionCount)

        verifyAll {
            preferences.getLong(keyAffiseSessionCount, 0L)
        }

    }
}