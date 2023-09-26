package com.affise.attribution.logs

/**
 * UseCase store logs interface
 */
internal interface StoreLogsUseCase {

    /**
     * Store log
     */
    fun storeLog(log: AffiseLog)
}