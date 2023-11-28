package com.affise.attribution.usecase

import android.os.Build
import java.util.Locale

internal class RemarketingUseCaseImpl : RemarketingUseCase {

    override val locale: String
    override val osAndVersion: String
    override val device: String
    override val build: String

    init {
        locale = locale()
        osAndVersion = osAndVersion()
        device = device()
        build = build()
    }

    private fun locale(): String {
        return Locale.getDefault().toString()
    }
    private fun osAndVersion(): String {
        return "Android ${Build.VERSION.RELEASE}"
    }

    private fun device(): String {
        return Build.MODEL
    }

    private fun build(): String {
        return "Build/${Build.ID}"
    }
}