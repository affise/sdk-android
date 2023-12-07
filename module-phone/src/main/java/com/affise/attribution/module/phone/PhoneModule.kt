package com.affise.attribution.module.phone

import com.affise.attribution.module.phone.parameters.IspNameProvider
import com.affise.attribution.module.phone.parameters.NetworkTypeProvider
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.parameters.base.PropertyProvider

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

    private var providers: List<PropertyProvider<*>> = emptyList()

    override fun start() {
        providers = listOfNotNull(
            ispNameProvider,
            networkTypeProvider,
        )
    }

    override fun providers(): List<PropertyProvider<*>> = providers
}