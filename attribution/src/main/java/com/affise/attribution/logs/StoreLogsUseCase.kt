package com.affise.attribution.logs

import com.affise.attribution.events.predefined.AffiseLog

/**
 * UseCase store logs interface
 */
internal interface StoreLogsUseCase {

    /**
     * Store log
     */
    fun storeLog(log: AffiseLog)
}