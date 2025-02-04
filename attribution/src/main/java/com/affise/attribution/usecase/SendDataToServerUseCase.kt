package com.affise.attribution.usecase

interface SendDataToServerUseCase {

    /**
     * Send
     */
    fun send(withDelay: Boolean, sendEmpty: Boolean = true)
}