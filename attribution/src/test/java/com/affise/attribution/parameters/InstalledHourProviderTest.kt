package com.affise.attribution.parameters

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [InstalledHourProvider]
 */
class InstalledHourProviderTest {
    @Test
    fun `verify provide when first install time returns decimal timestamp is stripped`() {
        val time = 1635176812345
        val packageInfo: PackageInfo = PackageInfo().apply {
            firstInstallTime = time
        }
        val packageName = "com.my.app"
        val packageManager: PackageManager = mockk {
            every {
                getPackageInfo(packageName, 0)
            } returns packageInfo
        }
        val context: Context = mockk {
            every {
                getPackageManager()
            } returns packageManager

            every {
                getPackageName()
            } returns packageName
        }
        val provider = InstalledHourProvider(context)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(1635174000000)

        verifyAll {
            context.packageManager
            context.packageName
            packageManager.getPackageInfo(packageName, 0)
        }

    }

    @Test
    fun `verify provide when getPackageInfo returns null`() {

        val packageName = "com.my.app"
        val packageManager: PackageManager = mockk {
            every {
                getPackageInfo(packageName, 0)
            } returns null
        }
        val context: Context = mockk {
            every {
                getPackageManager()
            } returns packageManager

            every {
                getPackageName()
            } returns packageName
        }
        val provider = InstalledHourProvider(context)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)

        verifyAll {
            context.packageManager
            context.packageName
            packageManager.getPackageInfo(packageName, 0)
        }

    }


}