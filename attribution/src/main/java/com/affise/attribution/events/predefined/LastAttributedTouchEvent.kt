package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import com.affise.attribution.events.property.AffisePropertyBuilder
import org.json.JSONObject

/**
 * Event LastAttributedTouch
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class LastAttributedTouchEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    private var touchType: TouchType? = null
    private var touchData: JSONObject? = null

    /**
     * Event LastAttributedTouch
     *
     * @property touchType type in CLICK, WEB_TO_APP_AUTO_REDIRECT, IMPRESSION
     * @property timeStampMillis the timestamp event in milliseconds.
     * @property touchData the JSON Object describing the meaning of the event.
     * @property userData any custom string data.
     */
    @Deprecated(
        message = "This constructor will be removed if future",
        replaceWith = ReplaceWith("LastAttributedTouchEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        touchType: TouchType,
        timeStampMillis: Long = System.currentTimeMillis(),
        touchData: JSONObject,
        userData: String? = null,
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        this.touchType = touchType
        this.touchData = touchData
    }

    override fun serializeBuilder(): AffisePropertyBuilder =
        super.serializeBuilder()
            .add("type", touchType)
            .add("data", touchData)
}