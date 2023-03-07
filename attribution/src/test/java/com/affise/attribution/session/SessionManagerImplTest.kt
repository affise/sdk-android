package com.affise.attribution.session

import android.content.SharedPreferences
import android.os.SystemClock
import com.affise.attribution.internal.StoreInternalEventUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import org.junit.Test

class SessionManagerImplTest {

    private val keyLifetimeSessionCount = "lifetime_session_count"
    private val keyAffiseSessionCount = "affise_session_count"

    private val saveLifetimeSessionCount = 10000L
    private val saveSessionCount = 10L

    private val timeStart = 0L
    private val timeStartSession = 15001L
    private val timeNotStartSession = 14999L
    private val timeHideSession = 1000L

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
            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns 0
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()
        val manager = SessionManagerImpl(preferences, provider, internalEventUseCase)
        val lifetimeSessionTime = manager.getLifetimeSessionTime()

        Truth.assertThat(lifetimeSessionTime).isEqualTo(saveLifetimeSessionCount)

        verifyAll {
            preferences.getLong(keyLifetimeSessionCount, 0L)
            preferences.getLong(keyAffiseSessionCount, 0L)
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
            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns 0
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        mockkStatic(SystemClock::class) {
            every {
                SystemClock.elapsedRealtime()
            } returns timeStart

            val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

            listenerTest(1)

            every {
                SystemClock.elapsedRealtime()
            } returns timeNotStartSession

            val lifetimeSessionTime = manager.getLifetimeSessionTime()

            Truth.assertThat(lifetimeSessionTime)
                .isEqualTo(saveLifetimeSessionCount + timeNotStartSession)

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
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
            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns 0
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        mockkStatic(SystemClock::class) {
            every {
                SystemClock.elapsedRealtime()
            } returns timeStart

            val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

            listenerTest(1)

            every {
                SystemClock.elapsedRealtime()
            } returns timeStartSession

            val lifetimeSessionTime = manager.getLifetimeSessionTime()

            Truth.assertThat(lifetimeSessionTime)
                .isEqualTo(saveLifetimeSessionCount + timeStartSession)

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
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
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        mockkStatic(SystemClock::class) {
            every {
                SystemClock.elapsedRealtime()
            } returns timeStart

            val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

            listenerTest(1)

            every {
                SystemClock.elapsedRealtime()
            } returns timeStartSession

            listenerTest(0)

            every {
                SystemClock.elapsedRealtime()
            } returns timeHideSession

            val lifetimeSessionTime = manager.getLifetimeSessionTime()

            Truth.assertThat(lifetimeSessionTime)
                .isEqualTo(saveLifetimeSessionCount - timeStart + timeStartSession)
            //.isEqualTo(saveLifetimeSessionCount)

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
                preferences.edit().putLong(keyLifetimeSessionCount, any()).commit()
                preferences.edit().putLong(keyAffiseSessionCount, 1).commit()
            }

        }
    }

    //--SessionTime---

    @Test
    fun getSessionTimeNotOpenActive() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns saveLifetimeSessionCount

            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns 0
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

        val sessionTime = manager.getSessionTime()

        Truth.assertThat(sessionTime).isEqualTo(0L)
    }

    @Test
    fun getSessionTimeNotStartSession() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns saveLifetimeSessionCount

            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns 0
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        mockkStatic(SystemClock::class) {
            every {
                SystemClock.elapsedRealtime()
            } returns timeStart

            val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

            listenerTest(1)

            every {
                SystemClock.elapsedRealtime()
            } returns timeNotStartSession

            val sessionTime = manager.getSessionTime()

            Truth.assertThat(sessionTime).isEqualTo(timeNotStartSession)

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
            }
        }
    }

    @Test
    fun getSessionTimeActive() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns saveLifetimeSessionCount

            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns 0
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        mockkStatic(SystemClock::class) {
            every {
                SystemClock.elapsedRealtime()
            } returns timeStart

            val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

            listenerTest(1)

            every {
                SystemClock.elapsedRealtime()
            } returns timeStartSession

            val sessionTime = manager.getSessionTime()

            Truth.assertThat(sessionTime).isEqualTo(timeStartSession)

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
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
                getLong(keyLifetimeSessionCount, 0L)
            } returns 0

            every {
                edit().putLong(keyAffiseSessionCount, any()).commit()
            } returns true
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        mockkStatic(SystemClock::class) {
            every {
                SystemClock.elapsedRealtime()
            } returns timeStart

            val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

            listenerTest(1)

            every {
                SystemClock.elapsedRealtime()
            } returns timeStartSession

            val isSessionActive = manager.isSessionActive()

            Truth.assertThat(isSessionActive).isTrue()

            verifyAll {
                preferences.getLong(keyAffiseSessionCount, 0L)
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.edit().putLong(keyAffiseSessionCount, any()).commit()
            }
        }
    }

    @Test
    fun isSessionNotActive() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns 0

            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns 0
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        mockkStatic(SystemClock::class) {
            every {
                SystemClock.elapsedRealtime()
            } returns timeStart

            val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

            listenerTest(1)

            every {
                SystemClock.elapsedRealtime()
            } returns timeNotStartSession

            val isSessionActive = manager.isSessionActive()

            Truth.assertThat(isSessionActive).isFalse()

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
            }
        }
    }

    @Test
    fun isSessionClose() {
        mockkStatic(SystemClock::class) {
            every {
                SystemClock.elapsedRealtime()
            } returns timeStart

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
            val internalEventUseCase: StoreInternalEventUseCase = mockk()

            val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

            listenerTest(1)

            every {
                SystemClock.elapsedRealtime()
            } returns timeStartSession

            listenerTest(0)

            every {
                SystemClock.elapsedRealtime()
            } returns timeHideSession

            val isSessionActive = manager.isSessionActive()

            Truth.assertThat(isSessionActive).isFalse()

            verifyAll {
                preferences.getLong(keyLifetimeSessionCount, 0L)
                preferences.getLong(keyAffiseSessionCount, 0L)
                preferences.edit().putLong(keyAffiseSessionCount, 1).commit()
                preferences.edit().putLong(keyLifetimeSessionCount, any()).commit()
            }
        }
    }

    @Test
    fun getSessionCount() {
        val preferences: SharedPreferences = mockk {
            every {
                getLong(keyLifetimeSessionCount, 0L)
            } returns 0

            every {
                getLong(keyAffiseSessionCount, 0L)
            } returns saveSessionCount
        }
        val internalEventUseCase: StoreInternalEventUseCase = mockk()

        val manager = SessionManagerImpl(preferences, provider, internalEventUseCase).apply { init() }

        val isSessionActive = manager.getSessionCount()

        Truth.assertThat(isSessionActive).isEqualTo(saveSessionCount)

        verifyAll {
            preferences.getLong(keyAffiseSessionCount, 0L)
            preferences.getLong(keyLifetimeSessionCount, 0L)
        }
    }
}