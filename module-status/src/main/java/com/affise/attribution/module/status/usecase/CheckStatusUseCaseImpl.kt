package com.affise.attribution.module.status.usecase

import com.affise.attribution.converter.Converter
import com.affise.attribution.exceptions.CloudException
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.modules.AffiseKeyValue
import com.affise.attribution.modules.OnKeyValueCallback
import com.affise.attribution.network.HttpClient
import com.affise.attribution.parameters.base.Provider
import java.net.URL


class CheckStatusUseCaseImpl(
    private val logsManager: LogsManager,
    private val httpClient: HttpClient?,
    private val sendServiceProvider: ExecutorServiceProvider,
    private val providers: List<Provider>,
    private val converter: Converter<@JvmSuppressWildcards List<Provider>, @JvmSuppressWildcards String>,
    private val keyValueConverter: Converter<@JvmSuppressWildcards String?, @JvmSuppressWildcards List<AffiseKeyValue>>,
) : CheckStatusUseCase {

    @Synchronized
    override fun send(onComplete: OnKeyValueCallback) {
        sendServiceProvider.provideExecutorService().execute {
            try {
                val response = createRequest(URL, providers)
                onComplete.handle(keyValueConverter.convert(response))
            } catch (throwable: Throwable) {
                //Log error
                logsManager.addSdkError(CloudException(URL, throwable, 1, false))
            }
        }
    }

    private fun createRequest(url: String, data: List<Provider>): String? {
        //Create request
        return httpClient?.executeRequest(
            URL(url),
            HttpClient.Method.POST,
            converter.convert(data),
            createHeaders()
        )
    }

    private fun createHeaders() = mapOf(
        "Content-Type" to "application/json; charset=utf-8"
    )

    companion object {
        const val URL = "https://tracking.affattr.com/check_status"
    }
}