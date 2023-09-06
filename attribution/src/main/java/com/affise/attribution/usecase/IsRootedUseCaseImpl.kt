package com.affise.attribution.usecase

import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


internal class IsRootedUseCaseImpl : IsRootedUseCase {

    private var rooted: Boolean? = null

    override fun isRooted(): Boolean {
        rooted?.let {
            return it
        }

        return (checkRootByTag() || checkRootByFile() || checkRootBySu()).also {
            rooted = it
        }
    }

    private fun checkRootByTag(): Boolean {
        return Build.TAGS?.contains("test-keys") ?: false
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
        ).forEach {
            if (File(it).exists()) return true
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
}