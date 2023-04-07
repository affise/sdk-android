package com.affise.attribution.parameters

import android.app.Application
import android.os.Build
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.utils.SystemAppChecker

/**
 * Provider for parameter [Parameters.STORE]
 *
 * @property app to retrieve package manager
 * @property logsManager for error logging
 * @property systemAppChecker to check system for preinstall
 */
class StoreProvider(
    private val app: Application,
    private val logsManager: LogsManager,
    private val systemAppChecker: SystemAppChecker
) : StringPropertyProvider() {

    override val order: Float = 5.0f
    override val key: String = Parameters.STORE

    /**
     * Installer name
     */
    private val installerName by lazy {
        when {
            !systemAppChecker.getSystemProperty(PREINSTALL_NAME).isNullOrEmpty() -> PREINSTALL
            systemAppChecker.isPreinstallApp() -> PREINSTALL
            else -> getInitiatingPackageName().let {
                when (it) {
                    PACKAGE_GOOGLE -> GOOGLE
                    PACKAGE_HUAWEI -> HUAWEI
                    PACKAGE_AMAZON -> AMAZON
                    else -> APK
                }
            }
        }
    }

    override fun provide(): String = installerName

    /**
     * Get initiating app package name
     */
    @Suppress("DEPRECATION")
    private fun getInitiatingPackageName() = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            app.packageManager.getInstallSourceInfo(app.packageName).initiatingPackageName
        } else {
            app.packageManager.getInstallerPackageName(app.packageName)
        }
    } catch (throwable: Throwable) {
        //log error
        logsManager.addDeviceError(throwable)

        null
    }

    companion object {
        private const val PREINSTALL_NAME = "affise_part_param_name"

        private const val PACKAGE_GOOGLE = "com.android.vending"
        private const val PACKAGE_HUAWEI = "com.huawei.appmarket"
        private const val PACKAGE_AMAZON = "com.amazon.venezia"

        private const val GOOGLE = "GooglePlay"
        private const val HUAWEI = "AppGalery"
        private const val AMAZON = "Amazon"
        private const val PREINSTALL = "Preinstall"
        private const val APK = "Apk"
    }
}