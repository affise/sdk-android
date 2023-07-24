package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Event StartTutorial
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class StartTutorialEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event StartTutorial
     *
     * @property tutorial the JSON Object describing the meaning of the event.
     * @property timeStampMillis the timestamp event in milliseconds.
     * @property userData any custom string data.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("StartTutorialEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        tutorial: JSONObject = JSONObject(),
        timeStampMillis: Long = timestamp(),
        userData: String? = null,
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = tutorial
    }
    override fun getName(): String = EventName.START_TUTORIAL.eventName
}