package com.affise.attribution.modules.exceptions

import com.affise.attribution.BuildConfig
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.AffiseModules

class AffiseModuleVersionException(
    name: AffiseModules,
    module: AffiseModule
) : Throwable("module [${name.toString().lowercase()}:${module.version}] version is incompatible with [attribution:${BuildConfig.AFFISE_VERSION}], use same version as attribution")