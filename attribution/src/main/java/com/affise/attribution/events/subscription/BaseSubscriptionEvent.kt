package com.affise.attribution.events.subscription

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Base Event of Subscription use [data] of event and [userData]
 */
abstract class BaseSubscriptionEvent(
    private val data: JSONObject,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Type of subscription
     *
     */
    abstract val type: String

    /**
     * Subtype of subscription
     */
    abstract val subtype: String

    /**
     * Serialize SubscriptionEvent to JSONObject
     *
     * @return JSONObject of SubscriptionEvent
     */
    override fun serialize() = JSONObject().apply {
        //Add subtype
        put(SubscriptionParameters.AFFISE_SUBSCRIPTION_EVENT_TYPE_KEY, subtype)

        //Add data
        data.keys().forEach { key ->
            put(key, data.get(key))
        }
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = type

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}