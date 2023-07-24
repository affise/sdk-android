package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Event ListView
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class ListViewEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event ListView
     *
     * @property list the JSON Object describing the meaning of the event.
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("ListViewEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        list: JSONObject = JSONObject(),
        userData: String? = null,
        timeStampMillis: Long = timestamp(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = list
    }
    override fun getName(): String = EventName.LIST_VIEW.eventName
}