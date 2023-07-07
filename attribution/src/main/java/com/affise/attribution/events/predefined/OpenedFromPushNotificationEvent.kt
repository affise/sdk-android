package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent

/**
 * Event OpenedFromPushNotification
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class OpenedFromPushNotificationEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event OpenedFromPushNotification
     *
     * @property details the describing the meaning of the event.
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed if future",
        replaceWith = ReplaceWith("OpenedFromPushNotificationEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        details: String = "",
        userData: String? = null,
        timeStampMillis: Long = System.currentTimeMillis(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = details
    }
}