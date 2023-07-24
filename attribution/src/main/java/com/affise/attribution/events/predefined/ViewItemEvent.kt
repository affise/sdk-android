package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Event ViewItem
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class ViewItemEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event ViewItem
     *
     * @property item the JSON Object describing the meaning of the event.
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("ViewItemEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        item: JSONObject = JSONObject(),
        userData: String? = null,
        timeStampMillis: Long = timestamp(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = item
    }
    override fun getName(): String = EventName.VIEW_ITEM.eventName
}