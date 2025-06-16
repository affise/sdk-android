package com.affise.attribution.modules

import com.affise.attribution.Affise

abstract class AffiseModuleApiWrapper<API : AffiseModuleApi>(
    private val module: AffiseModules,
) : AffiseHasModule {
    private var api: API? = null

    private fun <API : AffiseModuleApi> getApi(): API? {
        return Affise.api?.moduleManager?.api(module)
    }

    protected val moduleApi: API?
        get() = api ?: getApi<API>().also {
            api = it
        }

    override fun hasModule(): Boolean {
        return Affise.api?.moduleManager?.hasModule(module) ?: false
    }
}