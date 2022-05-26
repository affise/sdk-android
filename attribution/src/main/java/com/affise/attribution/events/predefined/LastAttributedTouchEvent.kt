package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event LastAttributedTouch
 *
 * @property touchType type in CLICK, WEB_TO_APP_AUTO_REDIRECT, IMPRESSION
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property touchData the JSON Object describing the meaning of the event.
 * @property userData any custom string data.
 */
class LastAttributedTouchEvent(
    private val touchType: TouchType,
    private val timeStampMillis: Long,
    private val touchData: JSONObject,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize LastAttributedTouchEvent to JSONObject
     *
     * @return JSONObject of LastAttributedTouchEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_last_attributed_touch_type", touchType)
        put("affise_event_last_attributed_touch_timestamp", timeStampMillis)
        put("affise_event_last_attributed_touch_data", touchData)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "LastAttributedTouch"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}