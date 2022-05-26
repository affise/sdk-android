package com.affise.attribution.events

import com.affise.attribution.session.CurrentActiveActivityCountProvider
import com.affise.attribution.usecase.SendDataToServerUseCase
import java.util.Timer
import java.util.TimerTask

/**
 * Manager of Events
 *
 * @property sendDataToServerUseCase the use case sending data to server.
 * @property activityCountProvider the provider observe count of open activity
 */
class EventsManager(
    private val sendDataToServerUseCase: SendDataToServerUseCase,
    private val activityCountProvider: CurrentActiveActivityCountProvider
) {

    /**
     * Last session count
     */
    private var lastSessionCount = 0L

    /**
     * Timer fo repeat send events
     */
    private var timer: Timer? = null

    /**
     * Start manager
     */
    fun init() = subscribeToActivityEvents()

    /**
     * Subscribe to change open activity count
     */
    private fun subscribeToActivityEvents() {
        activityCountProvider.addActivityCountListener { count ->
            //Check if start new session
            if (lastSessionCount == 0L && count == 1L) {
                //Send events on activity started
                sendEvents()

                //Start timer fo repeat send events
                startTimer()
            } else if (lastSessionCount == 1L && count == 0L) {
                //Stop timer
                stopTimer()
            }

            lastSessionCount = count
        }
    }

    /**
     * Send event
     */
    fun sendEvents() {
        sendDataToServerUseCase.send()
    }

    /**
     * Start timer fo repeat send events
     */
    private fun startTimer() {
        //Stop timer if running
        timer?.let {
            stopTimer()
        }

        //Create timer
        timer = Timer()

        //Start timer
        timer?.schedule(object : TimerTask() {
            override fun run() {
                //Send events
                sendEvents()

                //Stop timer
                stopTimer()
            }
        }, TIME_SEND_REPEAT)
    }

    /**
     * Stop timer fo repeat send events
     */
    private fun stopTimer() {
        //Stop timer
        timer?.cancel()
        timer = null
    }

    companion object {
        const val TIME_SEND_REPEAT = 15 * 1000L
    }
}
