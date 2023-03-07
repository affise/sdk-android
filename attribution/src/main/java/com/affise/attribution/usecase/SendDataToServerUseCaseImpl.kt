package com.affise.attribution.usecase

import com.affise.attribution.events.EventsParams
import com.affise.attribution.events.EventsRepository
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.logs.LogsRepository
import com.affise.attribution.metrics.MetricsRepository
import com.affise.attribution.network.CloudConfig
import com.affise.attribution.network.CloudRepository
import com.affise.attribution.parameters.factory.PostBackModelFactory
import com.affise.attribution.preferences.models.OfflineModeEnabledException
import com.affise.attribution.internal.InternalEventsRepository

internal class SendDataToServerUseCaseImpl(
    private val postBackModelFactory: PostBackModelFactory,
    private val cloudRepository: CloudRepository,
    private val eventsRepository: EventsRepository,
    private val internalEventsRepository: InternalEventsRepository,
    private val sendServiceProvider: ExecutorServiceProvider,
    private val logsRepository: LogsRepository,
    private val metricsRepository: MetricsRepository,
    private val logsManager: LogsManager,
    private val preferencesUseCase: PreferencesUseCase
) : SendDataToServerUseCase {

    /**
     * Flags to status is sending from current url
     */
    private val isSend = CloudConfig.getUrls()
        .map { it to false }
        .toMap(HashMap())

    /**
     * Send data
     */
    @Synchronized
    override fun send(withDelay: Boolean) {
        if (preferencesUseCase.isOfflineModeEnabled()) {
            logsManager.addSdkError(OfflineModeEnabledException())
            return
        }
        //For all urls
        CloudConfig.getUrls().forEach {
            //Check if sending on this url
            if (isSend[it] == false) {
                //Lock sending to this url
                isSend[it] = true

                sendServiceProvider.provideExecutorService().execute {
                    if (withDelay) {
                        Thread.sleep(TIME_DELAY_SENDING)
                    }

                    //Send to this url
                    try {
                        send(it)
                    } catch (throwable: Throwable) {
                        //Log error
                        logsManager.addSdkError(throwable)
                    }

                    //Unlock sending to this url
                    isSend[it] = false
                }
            }
        }
    }

    /**
     * Sending for url
     */
    private fun send(url: String) {
        do {
            //Get events
            val events = eventsRepository.getEvents(url)

            //Get logs
            val logs = logsRepository.getLogs(url)

            //Get metrics
            val metrics = metricsRepository.getMetrics(url)

            //Get internal events
            val internalEvents = internalEventsRepository.getEvents(url)

            //Generate data
            val data = listOf(postBackModelFactory.create(events, logs, metrics, internalEvents))

            try {
                //Send data for single url
                cloudRepository.send(data, url)

                //Remove sent events
                eventsRepository.deleteEvent(events.map { it.id }, url)

                //Remove sent logs
                logsRepository.deleteLogs(logs.map { it.id }, url)

                //Remove sent metrics
                metricsRepository.deleteMetrics(url)

                //Remove sent internal events
                internalEventsRepository.deleteEvent(internalEvents.map { it.id }, url)
            } catch (cloudException: Throwable) {
                //Log error
                logsManager.addNetworkError(cloudException)

                return
            }
        } while (events.size == EventsParams.EVENTS_SEND_COUNT)
    }

    companion object {
        private const val TIME_DELAY_SENDING: Long = 15000L
    }
}