package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp

/**
 * Event CompleteTrial
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class CompleteTrialEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {

    override fun getName(): String = EventName.COMPLETE_TRIAL.eventName
}