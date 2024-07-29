package com.affise.attribution.module.status

import com.affise.attribution.converter.PostBackModelToJsonStringConverter
import com.affise.attribution.converter.ProvidersToJsonStringConverter
import com.affise.attribution.converter.StringToKeyValueConverter
import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.module.status.usecase.CheckStatusUseCase
import com.affise.attribution.module.status.usecase.CheckStatusUseCaseImpl
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.OnKeyValueCallback
import com.affise.attribution.modules.exceptions.AffiseModuleError
import com.affise.attribution.network.HttpClient
import com.affise.attribution.parameters.factory.PostBackModelFactory


class StatusModule : AffiseModule() {

    override val version: String = BuildConfig.AFFISE_VERSION

    private var checkStatusUseCase: CheckStatusUseCase? = null

    override fun start() {
        val httpClient = get<HttpClient>()
        val providersToJsonConverter = get<ProvidersToJsonStringConverter>()
        val postBackModelToJsonStringConverter = get<PostBackModelToJsonStringConverter>()
        val postBackModelFactory = get<PostBackModelFactory>()

        if (
            httpClient == null ||
            providersToJsonConverter == null ||
            postBackModelToJsonStringConverter == null ||
            postBackModelFactory == null
        ) {
            AffiseModuleError.Init(this).printStackTrace()
            return
        }

        checkStatusUseCase = CheckStatusUseCaseImpl(
            module = this,
            logsManager = logsManager,
            httpClient = httpClient,
            sendServiceProvider = ExecutorServiceProviderImpl("Status Sending Worker"),
            converter = providersToJsonConverter,
            keyValueConverter = StringToKeyValueConverter(),
            postBackModelFactory = postBackModelFactory,
            postBackConverter = postBackModelToJsonStringConverter,
        )
    }

    override fun status(onComplete: OnKeyValueCallback) {
        checkStatusUseCase?.send(onComplete) ?: onComplete.handle(emptyList())
    }
}