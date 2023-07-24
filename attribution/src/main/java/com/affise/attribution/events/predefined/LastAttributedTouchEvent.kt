package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.events.property.AffiseProperty
import com.affise.attribution.events.property.AffisePropertyBuilder
import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Event LastAttributedTouch
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class LastAttributedTouchEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
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
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("LastAttributedTouchEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        touchType: TouchType,
        timeStampMillis: Long = timestamp(),
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
            .add(AffiseProperty.TYPE, touchType)
            .add(AffiseProperty.DATA, touchData)
    override fun getName(): String = EventName.LAST_ATTRIBUTED_TOUCH.eventName
}