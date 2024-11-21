package com.affise.attribution.usecase

import android.app.Application
import android.os.Build
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.utils.SystemAppChecker

class StoreUseCaseImpl(
    private val app: Application,
    private val logsManager: LogsManager,
    private val systemAppChecker: SystemAppChecker,
) : StoreUseCase {

    override fun getStore(): String {
        return installerName
    }

    /**
     * Installer name
     */
    private val installerName by lazy {
        when {
            !systemAppChecker.getSystemProperty(PREINSTALL_NAME)
                .isNullOrEmpty() -> StoreUseCase.PREINSTALL

            systemAppChecker.isPreinstallApp() -> StoreUseCase.PREINSTALL
            else -> getInitiatingPackageName().let {
                when (it) {
                    PACKAGE_GOOGLE -> StoreUseCase.GOOGLE
                    PACKAGE_HUAWEI -> StoreUseCase.HUAWEI
                    PACKAGE_AMAZON -> StoreUseCase.AMAZON
                    PACKAGE_RUSTORE -> StoreUseCase.RUSTORE
                    else -> StoreUseCase.APK
                }
            }
        }
    }

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
        private const val PACKAGE_RUSTORE = "ru.vk.store"
    }
}