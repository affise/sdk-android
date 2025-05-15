package com.affise.attribution.module.appsflyer

import com.affise.attribution.module.appsflyer.usecase.AppsFlyerUseCase
import com.affise.attribution.module.appsflyer.usecase.AppsFlyerUseCaseImpl
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.appsflyer.AffiseAppsFlyerApi


internal class AppsFlyerModule : AffiseModule(), AffiseAppsFlyerApi {

    override val version: String = BuildConfig.AFFISE_VERSION

    private val useCase: AppsFlyerUseCase by lazy {
        AppsFlyerUseCaseImpl()
    }

    override fun start() {
    }

    override fun logEvent(eventName: String, eventValues: Map<String, Any>) {
        useCase.logEvent(eventName, eventValues)
    }
}