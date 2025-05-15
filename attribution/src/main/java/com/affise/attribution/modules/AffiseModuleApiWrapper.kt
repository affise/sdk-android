package com.affise.attribution.modules

import com.affise.attribution.Affise

abstract class AffiseModuleApiWrapper<API : AffiseModuleApi>(
    module: AffiseModules
) {
    private var api: API? = null

    protected val moduleApi: API? = api ?: Affise.Module.api<API>(module).also {
        api = it
    }
}