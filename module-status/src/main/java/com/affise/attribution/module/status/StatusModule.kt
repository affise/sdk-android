package com.affise.attribution.module.status

import com.affise.attribution.converter.ProvidersToJsonStringConverter
import com.affise.attribution.converter.StringToKeyValueConverter
import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.module.status.usecase.CheckStatusUseCase
import com.affise.attribution.module.status.usecase.CheckStatusUseCaseImpl
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.OnKeyValueCallback
import com.affise.attribution.network.HttpClient


class StatusModule : AffiseModule() {

    override val version: String = BuildConfig.AFFISE_VERSION

    private var checkStatusUseCase: CheckStatusUseCase? = null

    override fun start() {
        val httpClient = get<HttpClient>() ?: return
        val providersToJsonConverter = get<ProvidersToJsonStringConverter>() ?: return

        checkStatusUseCase = CheckStatusUseCaseImpl(
            this,
            logsManager,
            httpClient,
            ExecutorServiceProviderImpl("Status Sending Worker"),
            providersToJsonConverter,
            StringToKeyValueConverter()
        )
    }

    override fun status(onComplete: OnKeyValueCallback) {
        checkStatusUseCase?.send(onComplete) ?: onComplete.handle(emptyList())
    }
}