package com.affise.attribution.usecase

import android.os.Build
import java.util.Locale

internal class RemarketingUseCaseImpl : RemarketingUseCase {

    override val osAndVersion: String
    override val device: String
    override val build: String

    init {
        osAndVersion = osAndVersion()
        device = device()
        build = build()
    }

    override fun locale(): String {
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