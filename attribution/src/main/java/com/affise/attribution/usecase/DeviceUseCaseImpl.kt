package com.affise.attribution.usecase

import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


internal class DeviceUseCaseImpl : DeviceUseCase {

    private var rooted: Boolean? = null
    private var emulator: Boolean? = null

    override fun isRooted(): Boolean {
        rooted?.let {
            return it
        }

        return (checkRootByTag() || checkRootByFile() || checkRootBySu()).also {
            rooted = it
        }
    }

    override fun isEmulator(): Boolean {
        emulator?.let {
            return it
        }

        return checkEmulator().also {
            emulator = it
        }
    }

    private fun checkRootByTag(): Boolean {
        return Build.TAGS?.contains("test-keys", ignoreCase = true) ?: false
    }

    private fun checkRootByFile(): Boolean {
        arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        ).ifTrue({
            File(it).exists()
        }) {
            return true
        }

        return false
    }

    private fun checkRootBySu(): Boolean {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val br = BufferedReader(InputStreamReader(process.inputStream))
            return br.readLine() != null
        } catch (_: Throwable) {
        } finally {
            process?.destroy()
        }

        return false
    }

    private fun checkEmulator(): Boolean {
        arrayOf(
            "google_sdk",
            "droid4x",
            "Emulator",
            "Android SDK built for x86"
        ).ifTrue({
            Build.MODEL.contains(it, ignoreCase = true)
        }) {
            return true
        }

        arrayOf(
            "goldfish",
            "vbox86",
            "nox"
        ).ifTrue({
            Build.HARDWARE.contains(it, ignoreCase = true)
        }) {
            return true
        }

        arrayOf(
            "sdk",
            "google_sdk",
            "sdk_x86",
            "vbox86p",
            "nox"
        ).ifTrue({
            Build.PRODUCT.contains(it, ignoreCase = true)
        }) {
            return true
        }

        arrayOf(
            Build.MANUFACTURER.contains("Genymotion", ignoreCase = true),
            Build.FINGERPRINT.startsWith("generic"),
            Build.BOARD.contains("nox", ignoreCase = true),
            Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
        ).ifTrue({ it }) {
            return true
        }

        return false
    }

    private inline fun <T> Array<out T>.ifTrue(predicate: (T) -> Boolean, ifTrueBlock: () -> Unit) {
        for (element in this) {
            if (predicate(element)) {
                ifTrueBlock()
                return
            }
        }
    }
}