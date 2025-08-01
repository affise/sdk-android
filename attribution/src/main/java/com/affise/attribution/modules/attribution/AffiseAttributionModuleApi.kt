package com.affise.attribution.modules.attribution

import com.affise.attribution.modules.AffiseModules
import com.affise.attribution.modules.OnKeyValueCallback

interface AffiseAttributionModuleApi {
    /**
     * Get module status
     */
    fun getStatus(module: AffiseModules, onComplete: OnKeyValueCallback)

    /**
     * Manual module start
     */
    fun moduleStart(module: AffiseModules): Boolean

    /**
     * Get installed modules
     */
    fun getModulesInstalled(): List<AffiseModules>
}