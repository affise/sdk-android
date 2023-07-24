package com.affise.attribution.events

import com.affise.attribution.events.property.AffiseProperty
import com.affise.attribution.events.property.AffisePropertyBuilder
import com.affise.attribution.utils.timestamp
import org.json.JSONObject


abstract class NativeEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
    protected var anyData: Any? = null,
) : Event() {

    protected open fun serializeBuilder(): AffisePropertyBuilder =
        AffisePropertyBuilder()
            .init(getName(), anyData)
            .add(AffiseProperty.TIMESTAMP, timeStampMillis)

    /**
     * Serialize Event to JSONObject
     *
     * @return JSONObject of Event
     */
    override fun serialize(): JSONObject = serializeBuilder().build()

    /**
     * Category of event
     *
     * @return category
     */
    override fun getCategory(): String = "native"


    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}