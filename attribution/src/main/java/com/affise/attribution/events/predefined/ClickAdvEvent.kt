package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp

/**
 * Event ClickAdv
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class ClickAdvEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event ClickAdv
     *
     * @property advertisement the describing the meaning of the event.
     * @property timeStampMillis the timestamp event in milliseconds.
     * @property userData any custom string data.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("ClickAdvEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        advertisement: String = "",
        timeStampMillis: Long = timestamp(),
        userData: String? = null,
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = advertisement
    }
    override fun getName(): String = EventName.CLICK_ADV.eventName
}