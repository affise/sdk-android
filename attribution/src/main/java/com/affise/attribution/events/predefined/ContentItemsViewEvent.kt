package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Event ContentItemsView
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class ContentItemsViewEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event ContentItemsView
     *
     * @property objects the list of JSON Object describing the meaning of the event.
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("ContentItemsViewEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        objects: List<JSONObject> = emptyList(),
        userData: String? = null,
        timeStampMillis: Long = timestamp(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = objects
    }
    override fun getName(): String = EventName.CONTENT_ITEMS_VIEW.eventName
}