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
            sendWithRepeat(onComplete) {
                Thread.sleep(TIME_DELAY_SENDING)
            }
        }
    }

    private fun sendWithRepeat(onComplete: OnKeyValueCallback, onFailedAttempt: () -> Unit) {
        //attempts to send
        var attempts = ATTEMPTS_TO_SEND

        //Send or not
        var send = false

        //While has attempts and not send
        while (attempts != 0 && !send) {
            try {
                val response = createRequest(URL, providers)
                onComplete.handle(keyValueConverter.convert(response))

                //Send is ok
                send = true
            } catch (throwable: Throwable) {
                //Check attempts
                if (--attempts == 0) {
                    onComplete.handle(emptyList())
                    //Log error
                    logsManager.addSdkError(CloudException(URL, throwable, ATTEMPTS_TO_SEND, true))
                } else {
                    onFailedAttempt()
                }
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
        private const val TIME_DELAY_SENDING: Long = 5000L
        private const val ATTEMPTS_TO_SEND = 30
        const val URL = "https://tracking.affattr.com/check_status"
    }
}