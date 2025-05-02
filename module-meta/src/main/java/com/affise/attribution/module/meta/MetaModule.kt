package com.affise.attribution.module.meta

import com.affise.attribution.init.AffiseInitProperties
import com.affise.attribution.module.meta.parameters.MetaProvider
import com.affise.attribution.module.meta.usecase.MetaReferrerUseCase
import com.affise.attribution.module.meta.usecase.MetaReferrerUseCaseImpl
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.exceptions.AffiseModuleError
import com.affise.attribution.parameters.base.PropertyProvider


internal class MetaModule : AffiseModule() {

    override val version: String = BuildConfig.AFFISE_VERSION

    private var initProperties: AffiseInitProperties? = null

    private val metaUseCase: MetaReferrerUseCase? by lazy {
        MetaReferrerUseCaseImpl(application, initProperties?.configValues)
    }

    private val metaProvider: PropertyProvider<*>? by lazy {
        MetaProvider(metaUseCase)
    }

    private var providers: List<PropertyProvider<*>> = emptyList()

    override fun start() {
        initProperties = get<AffiseInitProperties>()

        if (
            initProperties == null
        ) {
            AffiseModuleError.Init(this).printStackTrace()
            return
        }

        providers = listOfNotNull(
            metaProvider
        )
    }

    override fun providers(): List<PropertyProvider<*>> = providers
}