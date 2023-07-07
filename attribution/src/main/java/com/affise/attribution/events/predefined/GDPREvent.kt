package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import com.affise.attribution.events.property.AffisePropertyBuilder
import org.json.JSONObject

/**
 * Event GDPR
 *
 * @property userData any custom string data.
 */
internal class GDPREvent(
    private val userData: String? = null,
) : NativeEvent(
    userData = userData,
    timeStampMillis = System.currentTimeMillis()
) {

    /**
     * Serialize DeepLinkedEvent to JSONObject
     *
     * @return JSONObject of DeepLinkedEvent
     */
    override fun serialize(): JSONObject =
        AffisePropertyBuilder()
            .init(getName(), true)
            .build()

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = EVENT_NAME

    companion object {
        const val EVENT_NAME = "GDPR"
    }
}