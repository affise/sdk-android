package com.affise.attribution.module.huawei.oaid

import android.content.Context

/**
 * Manager Oaid interface
 */
interface OaidManager {
    /**
     * Start manager
     */
    fun init(app: Context?)

    /**
     * Get open advertising identifier
     *
     * @return OAID
     */
    fun getOaid(): String?
}