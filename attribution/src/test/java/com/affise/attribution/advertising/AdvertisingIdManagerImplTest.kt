package com.affise.attribution.advertising

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.google.common.truth.Truth
import com.google.common.util.concurrent.MoreExecutors
import io.mockk.*
import org.junit.Test

/**
 * Test for [AdvertisingIdManagerImpl]
 */
class AdvertisingIdManagerImplTest {

    @Test
    fun throwGetPackageInfo() {
        val exception = PackageManager.NameNotFoundException()

        val logsManager: LogsManager = mockk {
            every {
                addDeviceError(exception)
            } just Runs
        }

        val packageManagerMock: PackageManager = mockk {
            every {
                getPackageInfo(PACKAGE_VENDING, 0)
            } throws exception
        }

        val app: Application = mockk {
            every {
                packageManager
            } returns packageManagerMock
        }

        val connection = GoogleIdentifierConnection()

        val executorProvider: ExecutorServiceProvider = mockk {
            every {
                provideExecutorService()
            } returns MoreExecutors.newDirectExecutorService()
        }

        val manager = AdvertisingIdManagerImpl(
            connection, executorProvider, logsManager
        ).apply {
            init(app)
        }

        val result = manager.getAdvertisingId()
        Truth.assertThat(result).isNull()

        verifyAll {
            app.packageManager
            packageManagerMock.getPackageInfo(PACKAGE_VENDING, 0)
            logsManager.addDeviceError(exception)
        }
    }

    @Test
    fun notBindService() {
        val logsManager: LogsManager = mockk()

        val packageManager: PackageManager = mockk {
            every {
                getPackageInfo(PACKAGE_VENDING, 0)
            } returns null
        }

        val app: Application = mockk {
            every {
                getPackageManager()
            } returns packageManager

            every {
                bindService(any(), any(), Context.BIND_AUTO_CREATE)
            } returns false

            every {
                unbindService(any())
            } just Runs
        }

        mockkConstructor(Intent::class) {
            val intent = Intent()

            every {
                constructedWith<Intent>().setAction(INTENT_IDENTIFIER_SERVICE_START)
            } returns intent

            every {
                constructedWith<Intent>().setPackage(PACKAGE_GOOGLE_GMS)
            } returns intent

            val connection: GoogleIdentifierConnection = mockk()
            val executorProvider: ExecutorServiceProvider = mockk {
                every {
                    provideExecutorService()
                } returns MoreExecutors.newDirectExecutorService()
            }

            val manager = AdvertisingIdManagerImpl(
                connection, executorProvider, logsManager
            ).apply {
                init(app)
            }

            val result = manager.getAdvertisingId()

            Truth.assertThat(result).isNull()

            verifyAll {
                app.packageManager
                app.bindService(any(), any(), Context.BIND_AUTO_CREATE)
                app.unbindService(any())
                packageManager.getPackageInfo(PACKAGE_VENDING, 0)
            }
        }
    }

    @Test
    fun throwGetBinder() {
        val exception = IllegalStateException()

        val logsManager: LogsManager = mockk {
            every {
                addDeviceError(exception)
            } just Runs
        }

        val packageManager: PackageManager = mockk {
            every {
                getPackageInfo(PACKAGE_VENDING, 0)
            } returns null
        }

        val app: Application = mockk {
            every {
                getPackageManager()
            } returns packageManager

            every {
                bindService(any(), any(), Context.BIND_AUTO_CREATE)
            } returns true

            every {
                unbindService(any())
            } just Runs
        }

        mockkConstructor(Intent::class) {
            val intent = Intent()

            every {
                constructedWith<Intent>().setAction(INTENT_IDENTIFIER_SERVICE_START)
            } returns intent

            every {
                constructedWith<Intent>().setPackage(PACKAGE_GOOGLE_GMS)
            } returns intent

            val connection: GoogleIdentifierConnection = spyk {
                every {
                    getBinder()
                } throws exception
            }
            val executorProvider: ExecutorServiceProvider = mockk {
                every {
                    provideExecutorService()
                } returns MoreExecutors.newDirectExecutorService()
            }

            val manager = AdvertisingIdManagerImpl(
                connection, executorProvider, logsManager
            ).apply {
                init(app)
            }

            val result = manager.getAdvertisingId()

            Truth.assertThat(result).isNull()

            verifyAll {
                app.packageManager
                app.bindService(any(), any(), Context.BIND_AUTO_CREATE)
                app.unbindService(any())
                packageManager.getPackageInfo(PACKAGE_VENDING, 0)
                logsManager.addDeviceError(exception)
            }
        }
    }

    @Test
    fun createAaid() {
        val logsManager: LogsManager = mockk()

        val packageManager: PackageManager = mockk {
            every {
                getPackageInfo(PACKAGE_VENDING, 0)
            } returns null
        }

        val app: Application = mockk {
            every {
                getPackageManager()
            } returns packageManager

            every {
                bindService(any(), any(), Context.BIND_AUTO_CREATE)
            } returns true

            every {
                unbindService(any())
            } just Runs
        }

        mockkConstructor(Intent::class) {
            val intent = Intent()

            every {
                constructedWith<Intent>().setAction(INTENT_IDENTIFIER_SERVICE_START)
            } returns intent

            every {
                constructedWith<Intent>().setPackage(PACKAGE_GOOGLE_GMS)
            } returns intent

            mockkConstructor(GoogleIdentifierConnection::class) {
                val binder: IBinder = mockk()

                every {
                    constructedWith<GoogleIdentifierConnection>().getBinder()
                } returns binder

                mockkConstructor(GoogleIdentifierInterface::class) {
                    every {
                        constructedWith<GoogleIdentifierInterface>(EqMatcher(binder)).getGoogleAdid()
                    } returns ADID

                    val connection = GoogleIdentifierConnection()
                    val executorProvider: ExecutorServiceProvider = mockk {
                        every {
                            provideExecutorService()
                        } returns MoreExecutors.newDirectExecutorService()
                    }

                    val manager = AdvertisingIdManagerImpl(
                        connection, executorProvider, logsManager
                    ).apply {
                        init(app)
                    }

                    val result = manager.getAdvertisingId()

                    Truth.assertThat(result).isEqualTo(ADID)

                    verifyAll {
                        app.packageManager
                        app.bindService(any(), any(), Context.BIND_AUTO_CREATE)
                        app.unbindService(any())
                        packageManager.getPackageInfo(PACKAGE_VENDING, 0)
                    }
                }
            }
        }
    }

    companion object {
        private const val ADID = "adid"
        private const val PACKAGE_VENDING = "com.android.vending"
        private const val PACKAGE_GOOGLE_GMS = "com.google.android.gms"
        private const val INTENT_IDENTIFIER_SERVICE_START =
            "com.google.android.gms.ads.identifier.service.START"
    }
}