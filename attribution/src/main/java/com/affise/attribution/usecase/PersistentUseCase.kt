package com.affise.attribution.usecase

import com.affise.attribution.modules.AffiseModuleManager

interface PersistentUseCase {
    fun init(moduleManager: AffiseModuleManager)
    fun getAffDeviceId(): String?
}