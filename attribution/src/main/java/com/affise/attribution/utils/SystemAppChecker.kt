package com.affise.attribution.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.Scanner
import java.util.regex.Pattern
import kotlin.collections.HashSet
import kotlin.collections.MutableSet

class SystemAppChecker(private val app: Application) {

    /**
     * Check app if it system app
     *
     * @return is app is system
     */
    fun isPreinstallApp() = isSystemAppByFLAG() ||
        isSystemAppByPM() ||
        isSystemPreloaded() ||
        isSystemSigned()

    /**
     * Check if system app by 'pm' command-line program use
     *
     * @return is app is system
     */
    private fun isSystemAppByPM(): Boolean {
        val builder = ProcessBuilder("pm", "list", "packages", "-s")

        val process: Process = try {
            builder.start()
        } catch (e: IOException) {
            return false
        }
        val inputStream = process.inputStream
        val systemApps: MutableSet<String> = HashSet()

        Scanner(inputStream).use {
            val pattern = Pattern.compile("^package:.+")
            val skip = "package:".length

            while (it.hasNext(pattern)) {
                systemApps.add(it.next().substring(skip))
            }
        }

        return systemApps.contains(app.packageName)
    }

    /**
     * Check if application is preloaded.
     *
     * @return `true` if package is preloaded.
     */
    private fun isSystemPreloaded() = try {
        app.packageManager.getApplicationInfo(app.packageName, 0).let {
            it.sourceDir.startsWith("/system/app/") || it.sourceDir.startsWith("/system/priv-app/")
        }
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    /**
     * Check if the app is system signed or not
     *
     * @return `true` if application is signed by system certificate,
     */
    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures")
    private fun isSystemSigned() = try {
        // Get packageInfo for target application
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Get packageInfo for target application
            val packageInfo =
                app.packageManager.getPackageInfo(
                    app.packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
            // Get packageInfo for system package
            val sys =
                app.packageManager.getPackageInfo(
                    "android",
                    PackageManager.GET_SIGNING_CERTIFICATES
                )

            val signaturesApp = if (packageInfo.signingInfo.hasMultipleSigners()) {
                packageInfo.signingInfo.apkContentsSigners
            } else {
                packageInfo.signingInfo.signingCertificateHistory
            }

            val signaturesSys = if (sys.signingInfo.hasMultipleSigners()) {
                sys.signingInfo.apkContentsSigners
            } else {
                sys.signingInfo.signingCertificateHistory
            }
            // Match both packageInfo for there signatures
            signaturesApp.firstOrNull()?.let { it == signaturesSys.firstOrNull() } ?: false
        } else {
            // Get packageInfo for target application
            val packageInfo =
                app.packageManager.getPackageInfo(app.packageName, PackageManager.GET_SIGNATURES)
            // Get packageInfo for system package
            val sys = app.packageManager.getPackageInfo("android", PackageManager.GET_SIGNATURES)
            // Match both packageInfo for there signatures
            packageInfo?.signatures?.firstOrNull()?.let { it == sys.signatures.firstOrNull() }
                ?: false
        }
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    /**
     * Check if application is installed in the device's system image
     *.
     * @return is app is system
     */
    private fun isSystemAppByFLAG() = try {
        val applicationFlags = app.packageManager.getApplicationInfo(app.packageName, 0).flags
        val systemFlags = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP

        // Check if FLAG_SYSTEM or FLAG_UPDATED_SYSTEM_APP are set.
        applicationFlags and systemFlags != 0
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    /**
     * Check if app has flag in SystemProperties
     */
    @SuppressLint("PrivateApi")
    fun getSystemProperty(key: String) = try {
        Class.forName("android.os.SystemProperties")
            ?.getDeclaredMethod("get", String::class.java)
            ?.invoke(null, key)
            ?.toString()
    } catch (throwable: Throwable) {
        null
    }
}