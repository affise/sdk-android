package com.affise.attribution.unity

import com.affise.attribution.logs.LogsManager

internal class UnityLogsManager : LogsManager {
    override fun addNetworkError(throwable: Throwable) {
        throw throwable
    }

    override fun addDeviceError(throwable: Throwable) {
        throw throwable
    }

    override fun addDeviceError(message: String) {
        throw Exception(message)
    }

    override fun addUserError(throwable: Throwable) {
        throw throwable
    }

    override fun addSdkError(throwable: Throwable) {
        throw throwable
    }
}