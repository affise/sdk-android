package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent

/**
 * Event CustomId07
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class CustomId07Event(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {

    /**
     * Event CustomId07
     *
     * @property custom the describing the meaning of the event.
     * @property timeStampMillis the timestamp event in milliseconds.
     * @property userData any custom string data.
     */
    @Deprecated(
        message = "This constructor will be removed if future",
        replaceWith = ReplaceWith("CustomId07Event(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        custom: String = "",
        timeStampMillis: Long = System.currentTimeMillis(),
        userData: String? = null,
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = custom
    }
}