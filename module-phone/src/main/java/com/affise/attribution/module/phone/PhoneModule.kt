package com.affise.attribution.module.phone

import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.module.phone.parameters.IspNameProvider
import com.affise.attribution.module.phone.parameters.NetworkTypeProvider

class PhoneModule : AffiseModule() {

    private val ispNameProvider: PropertyProvider<*>? by lazy {
        application?.let { app ->
            IspNameProvider(app)
        }
    }

    private val networkTypeProvider: PropertyProvider<*>? by lazy {
        application?.let { app ->
            NetworkTypeProvider(app)
        }
    }

    override fun init( logsManager: LogsManager) {
    }

    override fun providers(): List<PropertyProvider<*>> = listOfNotNull(
        ispNameProvider,
        networkTypeProvider,
    )
}