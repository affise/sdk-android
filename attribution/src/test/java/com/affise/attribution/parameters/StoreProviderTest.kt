package com.affise.attribution.parameters

import android.app.Application
import android.content.pm.InstallSourceInfo
import android.content.pm.PackageManager
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.utils.SystemAppChecker
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test

/**
 * Test fore [StoreProvider]
 */
@Suppress("DEPRECATION")
class StoreProviderTest {

    private val packageNameTest = "com.my.app"
    private val testException = PackageManager.NameNotFoundException()

    private val packageManagerMockk: PackageManager = mockk {
        every { getInstallerPackageName(packageNameTest) } throws testException
    }
    private val appMockk: Application = mockk {
        every { packageManager } returns packageManagerMockk
        every { packageName } returns packageNameTest
    }
    private val logsManagerMockk: LogsManager = mockk {
        every { addDeviceError(testException) } just Runs
    }
    private val systemAppCheckerMockk: SystemAppChecker = mockk {
        every { getSystemProperty(PREINSTALL_NAME) } returns ""
        every { isPreinstallApp() } returns false
    }

    private val provider = StoreProvider(appMockk, logsManagerMockk, systemAppCheckerMockk)

    @Test
    fun `verify APK`() {
        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(APK)

        verifyAll {
            systemAppCheckerMockk.getSystemProperty(PREINSTALL_NAME)
            systemAppCheckerMockk.isPreinstallApp()
            appMockk.packageName
            appMockk.packageManager
            packageManagerMockk.getInstallerPackageName(packageNameTest)
            logsManagerMockk.addDeviceError(testException)
        }
    }

    @Test
    fun `verify installer google`() {
        val installSourceInfoMockk: InstallSourceInfo = mockk {
            every {
                initiatingPackageName
            } returns packageNameTest
        }

        every {
            packageManagerMockk.getInstallerPackageName(packageNameTest)
        } returns PACKAGE_GOOGLE

        every {
            packageManagerMockk.getInstallSourceInfo(PACKAGE_GOOGLE)
        } returns installSourceInfoMockk

        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(GOOGLE)

        verifyAll {
            systemAppCheckerMockk.getSystemProperty(PREINSTALL_NAME)
            systemAppCheckerMockk.isPreinstallApp()
            appMockk.packageName
            appMockk.packageManager
            packageManagerMockk.getInstallerPackageName(packageNameTest)
        }

    }

    @Test
    fun `verify preinstall`() {
        every {
            systemAppCheckerMockk.isPreinstallApp()
        } returns true

        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(PREINSTALL)

        verifyAll {
            systemAppCheckerMockk.getSystemProperty(PREINSTALL_NAME)
            systemAppCheckerMockk.isPreinstallApp()
        }
    }

    @Test
    fun `verify has system property`() {
        every {
            systemAppCheckerMockk.getSystemProperty(PREINSTALL_NAME)
        } returns "value"

        val actual = provider.provide()

        Truth.assertThat(actual).isEqualTo(PREINSTALL)

        verifyAll {
            systemAppCheckerMockk.getSystemProperty(PREINSTALL_NAME)
        }
    }

    companion object {
        private const val PREINSTALL_NAME = "affise_part_param_name"
        private const val PACKAGE_GOOGLE = "com.android.vending"

        private const val GOOGLE = "GooglePlay"
        private const val PREINSTALL = "Preinstall"
        private const val APK = "Apk"
    }

}