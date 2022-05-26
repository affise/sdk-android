package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event AddToWishlist
 *
 * @property wishList the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class AddToWishlistEvent(
    private val wishList: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize AddToWishlistEvent to JSONObject
     *
     * @return JSONObject of AddToWishlistEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_add_to_wishlist", wishList)
        put("affise_event_add_to_wishlist_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "AddToWishlist"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}