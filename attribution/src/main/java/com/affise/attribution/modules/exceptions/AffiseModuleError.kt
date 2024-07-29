package com.affise.attribution.modules.exceptions

import com.affise.attribution.BuildConfig
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.AffiseModules

sealed class AffiseModuleError : Throwable() {

    class Version(
        name: AffiseModules,
        module: AffiseModule,
    ) : Throwable(
        "module [${
            name.toString().lowercase()
        }:${module.version}] version is incompatible with [attribution:${BuildConfig.AFFISE_VERSION}], use same version as attribution"
    )

    class Init(
        module: AffiseModule,
    ) : Throwable("module [${AffiseModules.from(module.javaClass.name)?.name ?: module.javaClass.simpleName}] init failed")

}