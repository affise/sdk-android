package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent

/**
 * Event DeepLinked
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class DeepLinkedEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event DeepLinked
     *
     * @property isLinked event from link or nor
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed if future",
        replaceWith = ReplaceWith("DeepLinkedEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        isLinked: Boolean = false,
        userData: String? = null,
        timeStampMillis: Long = System.currentTimeMillis(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = isLinked
    }
}