package com.affise.attribution.usecase

import com.affise.attribution.converter.Converter
import com.affise.attribution.events.Event
import com.affise.attribution.events.OnSendFailedCallback
import com.affise.attribution.events.OnSendSuccessCallback
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.network.CloudConfig
import com.affise.attribution.network.CloudRepository
import com.affise.attribution.network.HttpResponse
import com.affise.attribution.parameters.factory.PostBackModelFactory
import com.affise.attribution.utils.isHttpValid


internal class ImmediateSendToServerUseCaseImpl(
    private val sendServiceProvider: ExecutorServiceProvider,
    private val cloudRepository: CloudRepository,
    private val postBackModelFactory: PostBackModelFactory,
    private val converterToSerializedEvent: Converter<Event, SerializedEvent>,
    private val logsManager: LogsManager,
) : ImmediateSendToServerUseCase {

    override fun sendNow(
        event: Event,
        success: OnSendSuccessCallback,
        failed: OnSendFailedCallback,
    ) {
        //For all urls
        CloudConfig.getUrls().forEach { url ->
            sendServiceProvider.provideExecutorService().execute {
                //Send to this url
                val response = sendNow(event, url)

                if (response.isHttpValid()) {
                    success.handle()
                } else {
                    failed.handle(response)
                }
            }
        }
    }

    private fun sendNow(event: Event, url: String): HttpResponse {
        try {
            //Serialize event
            val serializedEvent = converterToSerializedEvent.convert(event)

            //Generate data
            val data = postBackModelFactory.create(
                listOf(serializedEvent),
                emptyList(),
                emptyList(),
                emptyList()
            )

            //Send data for single url
            return cloudRepository.createRequest(url, listOf(data))
        } catch (exception: Throwable) {
            //Log error
            logsManager.addNetworkError(exception)

            return HttpResponse(
                code = 0,
                message = exception.localizedMessage ?: exception.message
                ?: exception.toString()
            )
        }
    }
}