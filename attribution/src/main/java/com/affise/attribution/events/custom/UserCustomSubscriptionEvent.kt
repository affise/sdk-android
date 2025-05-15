package com.affise.attribution.events.custom

import com.affise.attribution.events.Event
import com.affise.attribution.events.subscription.BaseSubscriptionEvent
import org.json.JSONObject

/**
 * Event UserCustomSubscription
 * @property type event type.
 * @property subtype event subtype.
 * @property data event data.
 * @property userData any custom string data.
 */
class UserCustomSubscriptionEvent(
    override val type: String,
    override val subtype: String,
    data: JSONObject,
    userData: String? = null,
    private val category: String? = null,
) : BaseSubscriptionEvent(data, userData) {

    override fun getCategory(): String = category ?: super.getCategory()

    /**
     * For internal use only
     */
    fun internalAddRawParameters(parameters: Map<String, Any>): Event {
        addRawParameters(parameters)
        return this
    }
}