package com.affise.attribution.module.status

import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.module.status.converter.ProvidersToJsonStringConverter
import com.affise.attribution.converter.StringToKeyValueConverter
import com.affise.attribution.module.status.usecase.CheckStatusUseCase
import com.affise.attribution.module.status.usecase.CheckStatusUseCaseImpl
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.OnKeyValueCallback
import com.affise.attribution.network.HttpClient
import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.parameters.base.Provider
import com.affise.attribution.parameters.providers.AffAppTokenPropertyProvider
import com.affise.attribution.parameters.providers.AffiseAppIdProvider
import com.affise.attribution.parameters.providers.AffiseDeviceIdProvider
import com.affise.attribution.parameters.providers.AffisePackageAppNameProvider
import com.affise.attribution.parameters.providers.CreatedTimeProvider
import com.affise.attribution.parameters.providers.RandomUserIdProvider

class StatusModule : AffiseModule() {

    private var checkStatusUseCase: CheckStatusUseCase? = null

    override fun init(logsManager: LogsManager) {
        val providers: List<Provider> = listOfNotNull(
            getProvider<CreatedTimeProvider>(),
            getProvider<AffiseAppIdProvider>(),
            getProvider<AffisePackageAppNameProvider>(),
            getProvider<AffAppTokenPropertyProvider>(),
            getProvider<AffiseDeviceIdProvider>(),
            getProvider<RandomUserIdProvider>(),
        )

        checkStatusUseCase = CheckStatusUseCaseImpl(
            logsManager,
            get<HttpClient>(),
            ExecutorServiceProviderImpl("Status Sending Worker"),
            providers,
            ProvidersToJsonStringConverter(),
            StringToKeyValueConverter()
        )
    }

    override fun providers(): List<PropertyProvider<*>> = listOfNotNull()

    override fun status(onComplete: OnKeyValueCallback) {
        checkStatusUseCase?.send(onComplete)
    }
}