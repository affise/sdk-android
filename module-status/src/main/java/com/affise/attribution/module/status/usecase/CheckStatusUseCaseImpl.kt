package com.affise.attribution.module.status.usecase

import com.affise.attribution.converter.ProvidersToJsonStringConverter
import com.affise.attribution.converter.StringToKeyValueConverter
import com.affise.attribution.exceptions.CloudException
import com.affise.attribution.exceptions.NetworkException
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.OnKeyValueCallback
import com.affise.attribution.network.CloudConfig
import com.affise.attribution.network.HttpClient
import com.affise.attribution.network.HttpResponse
import com.affise.attribution.parameters.base.Provider
import com.affise.attribution.utils.isHttpValid
import java.net.URL


class CheckStatusUseCaseImpl(
    affiseModule: AffiseModule,
    private val logsManager: LogsManager,
    private val httpClient: HttpClient,
    private val sendServiceProvider: ExecutorServiceProvider,
    private val converter: ProvidersToJsonStringConverter,
    private val keyValueConverter: StringToKeyValueConverter,
) : CheckStatusUseCase {

    private val providers: List<Provider> = affiseModule.getRequestProviders()

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
            val response = createRequest()
            if (isHttpValid(response.code)) {
                onComplete.handle(keyValueConverter.convert(response.body))

                //Send is ok
                send = true
            } else {
                //Check attempts
                if (--attempts == 0) {

                    onComplete.handle(emptyList())
                    //Log error
                    logsManager.addSdkError(
                        CloudException(
                            URL,
                            NetworkException(response.code, response.body ?: ""),
                            ATTEMPTS_TO_SEND,
                            true
                        )
                    )
                } else {
                    onFailedAttempt()
                }
            }
        }
    }

    private fun createRequest(): HttpResponse {
        //Create request
        return httpClient.executeRequest(
            URL(URL),
            HttpClient.Method.POST,
            converter.convert(providers),
            CloudConfig.headers
        )
    }

    companion object {
        private const val TIME_DELAY_SENDING: Long = 5000L
        private const val ATTEMPTS_TO_SEND = 30

        const val URL = "https://tracking.affattr.com/check_status"
    }
}