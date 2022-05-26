package com.affise.attribution.logs

/**
 * Manager logs interface
 */
interface LogsManager {

    /**
     * Add [throwable] of network
     */
    fun addNetworkError(throwable: Throwable)

    /**
     * Add [throwable] of device
     */
    fun addDeviceError(throwable: Throwable)

    /**
     * Add [throwable] of user
     */
    fun addUserError(throwable: Throwable)

    /**
     * Add [throwable] of sdk
     */
    fun addSdkError(throwable: Throwable)

    /**
     * Add [message] error of device
     */
    fun addDeviceError(message: String)
}