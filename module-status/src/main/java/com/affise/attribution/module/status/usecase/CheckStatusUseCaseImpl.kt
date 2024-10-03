package com.affise.attribution.module.status.usecase

import com.affise.attribution.converter.PostBackModelToJsonStringConverter
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
import com.affise.attribution.parameters.factory.PostBackModelFactory
import com.affise.attribution.utils.isHttpValid
import java.net.URL


class CheckStatusUseCaseImpl(
    module: AffiseModule,
    private val logsManager: LogsManager?,
    private val httpClient: HttpClient,
    private val sendServiceProvider: ExecutorServiceProvider,
    private val converter: ProvidersToJsonStringConverter,
    private val keyValueConverter: StringToKeyValueConverter,
    private val postBackModelFactory: PostBackModelFactory,
    private val postBackConverter: PostBackModelToJsonStringConverter,
) : CheckStatusUseCase {

    private val providers: List<Provider> = module.getRequestProviders()
    private val url: String = CloudConfig.getURL(PATH)
    private var isPostBackSend: Boolean = false

    @Synchronized
    override fun send(onComplete: OnKeyValueCallback) {
        sendServiceProvider.provideExecutorService().execute {
            sendWithRepeat(onComplete) { attempt ->
                Thread.sleep(TIME_DELAY * TIMINGS.getOrElse(attempt) { 1 })
            }
        }
    }

    private fun sendWithRepeat(onComplete: OnKeyValueCallback, onFailedAttempt: (Int) -> Unit) {
        //attempts to send
        var attempts = TIMINGS.size + 1

        //Send or not
        var send = false

        //While has attempts and not send
        while (attempts != 0 && !send) {
            val postBackResponse = if (!isPostBackSend) {
                createRequest(postBackData()).also {
                    isPostBackSend = it.isHttpValid()
                }
            } else null

            val response = if (isPostBackSend) {
                createRequest(providersData())
            } else null

            if (isPostBackSend && response?.isHttpValid() == true) {
                onComplete.handle(keyValueConverter.convert(response.body))

                //Send is ok
                send = true
            } else {
                attempts--
                //Check attempts
                if (attempts <= 0) {

                    onComplete.handle(emptyList())

//                    val httpResponse = response ?: postBackResponse
//
//                    //Log error
//                    logsManager?.addSdkError(
//                        CloudException(
//                            url,
//                            NetworkException(httpResponse?.code ?: 0, httpResponse?.body ?: ""),
//                            TIMINGS.size + 1,
//                            true
//                        )
//                    )
                } else {
                    onFailedAttempt(attempts - 1)
                }
            }
        }
    }

    private fun createRequest(data: String): HttpResponse {
        //Create request
        return httpClient.executeRequest(
            URL(url),
            HttpClient.Method.POST,
            data,
            CloudConfig.headers
        )
    }

    private fun providersData(): String {
        return converter.convert(providers)
    }

    private fun postBackData(): String {
        val data = postBackModelFactory.create(
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList()
        )
        return postBackConverter.convert(listOf(data))
    }

    companion object {
        private val TIMINGS = listOf(1,1,2,3,5,8,13).reversed()
        private const val TIME_DELAY: Long = 1000L

        const val PATH = "check_status"
    }
}