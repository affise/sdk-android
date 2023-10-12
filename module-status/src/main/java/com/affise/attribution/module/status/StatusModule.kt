package com.affise.attribution.module.status

import com.affise.attribution.converter.ProvidersToJsonStringConverter
import com.affise.attribution.converter.StringToKeyValueConverter
import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.module.status.usecase.CheckStatusUseCase
import com.affise.attribution.module.status.usecase.CheckStatusUseCaseImpl
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.OnKeyValueCallback
import com.affise.attribution.network.HttpClient
import com.affise.attribution.parameters.base.PropertyProvider


class StatusModule : AffiseModule() {

    private var checkStatusUseCase: CheckStatusUseCase? = null

    override fun init(logsManager: LogsManager) {
        val httpClient = get<HttpClient>()
        httpClient ?: return
        val providersToJsonConverter = get<ProvidersToJsonStringConverter>()
        providersToJsonConverter ?: return

        checkStatusUseCase = CheckStatusUseCaseImpl(
            this,
            logsManager,
            httpClient,
            ExecutorServiceProviderImpl("Status Sending Worker"),
            providersToJsonConverter,
            StringToKeyValueConverter()
        )
    }

    override fun providers(): List<PropertyProvider<*>> = listOfNotNull()

    override fun status(onComplete: OnKeyValueCallback) {
        checkStatusUseCase?.send(onComplete) ?: onComplete.handle(emptyList())
    }
}