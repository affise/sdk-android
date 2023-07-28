package com.affise.attribution.internal.events

import com.affise.attribution.events.Event
import com.affise.attribution.events.EventName
import com.affise.attribution.events.predefined.AchieveLevelEvent
import com.affise.attribution.events.predefined.AddPaymentInfoEvent
import com.affise.attribution.events.predefined.AddToCartEvent
import com.affise.attribution.events.predefined.AddToWishlistEvent
import com.affise.attribution.events.predefined.ClickAdvEvent
import com.affise.attribution.events.predefined.CompleteRegistrationEvent
import com.affise.attribution.events.predefined.CompleteStreamEvent
import com.affise.attribution.events.predefined.CompleteTrialEvent
import com.affise.attribution.events.predefined.CompleteTutorialEvent
import com.affise.attribution.events.predefined.ContactEvent
import com.affise.attribution.events.predefined.ContentItemsViewEvent
import com.affise.attribution.events.predefined.CustomId01Event
import com.affise.attribution.events.predefined.CustomId02Event
import com.affise.attribution.events.predefined.CustomId03Event
import com.affise.attribution.events.predefined.CustomId04Event
import com.affise.attribution.events.predefined.CustomId05Event
import com.affise.attribution.events.predefined.CustomId06Event
import com.affise.attribution.events.predefined.CustomId07Event
import com.affise.attribution.events.predefined.CustomId08Event
import com.affise.attribution.events.predefined.CustomId09Event
import com.affise.attribution.events.predefined.CustomId10Event
import com.affise.attribution.events.predefined.CustomizeProductEvent
import com.affise.attribution.events.predefined.DeepLinkedEvent
import com.affise.attribution.events.predefined.DonateEvent
import com.affise.attribution.events.predefined.FindLocationEvent
import com.affise.attribution.events.predefined.InitiateCheckoutEvent
import com.affise.attribution.events.predefined.InitiatePurchaseEvent
import com.affise.attribution.events.predefined.InitiateStreamEvent
import com.affise.attribution.events.predefined.InviteEvent
import com.affise.attribution.events.predefined.LastAttributedTouchEvent
import com.affise.attribution.events.predefined.LeadEvent
import com.affise.attribution.events.predefined.ListViewEvent
import com.affise.attribution.events.predefined.LoginEvent
import com.affise.attribution.events.predefined.OpenedFromPushNotificationEvent
import com.affise.attribution.events.predefined.OrderCancelEvent
import com.affise.attribution.events.predefined.OrderEvent
import com.affise.attribution.events.predefined.OrderReturnRequestCancelEvent
import com.affise.attribution.events.predefined.OrderReturnRequestEvent
import com.affise.attribution.events.predefined.PurchaseEvent
import com.affise.attribution.events.predefined.RateEvent
import com.affise.attribution.events.predefined.ReEngageEvent
import com.affise.attribution.events.predefined.ReserveEvent
import com.affise.attribution.events.predefined.SalesEvent
import com.affise.attribution.events.predefined.ScheduleEvent
import com.affise.attribution.events.predefined.SearchEvent
import com.affise.attribution.events.predefined.ShareEvent
import com.affise.attribution.events.predefined.SpendCreditsEvent
import com.affise.attribution.events.predefined.StartRegistrationEvent
import com.affise.attribution.events.predefined.StartTrialEvent
import com.affise.attribution.events.predefined.StartTutorialEvent
import com.affise.attribution.events.predefined.SubmitApplicationEvent
import com.affise.attribution.events.predefined.SubscribeEvent
import com.affise.attribution.events.predefined.TouchType
import com.affise.attribution.events.predefined.TravelBookingEvent
import com.affise.attribution.events.predefined.UnlockAchievementEvent
import com.affise.attribution.events.predefined.UnsubscribeEvent
import com.affise.attribution.events.predefined.UpdateEvent
import com.affise.attribution.events.predefined.ViewAdvEvent
import com.affise.attribution.events.predefined.ViewCartEvent
import com.affise.attribution.events.predefined.ViewContentEvent
import com.affise.attribution.events.predefined.ViewItemEvent
import com.affise.attribution.events.predefined.ViewItemsEvent
import com.affise.attribution.events.predefined.toTouchType
import com.affise.attribution.events.property.AffiseProperty
import com.affise.attribution.internal.utils.getPropertyByKey
import com.affise.attribution.internal.utils.getTimeStamp
import com.affise.attribution.internal.utils.getUserData
import com.affise.attribution.internal.utils.toJSONObject
import com.affise.attribution.utils.timestamp
import org.json.JSONArray
import org.json.JSONObject

@Deprecated(
    message = "Will be removed",
    level = DeprecationLevel.WARNING
)
internal object PredefinedEventSecondary {

    fun isSecondaryConstructor(eventName: String?, map: Map<*, *>): Boolean {
        if (map.getPropertyByKey<Any>() != null) return true
        if (eventName != EventName.LAST_ATTRIBUTED_TOUCH.eventName) return false
        if (map.getPropertyByKey<Any>(AffiseProperty.TYPE.type) != null) return true

        return false
    }

    fun create(name: String?, map: Map<*, *>): Event? {
        val eventName = EventName.from(name)
        eventName ?: return null
        val timeStamp = map.getTimeStamp() ?: timestamp()
        val userData = map.getUserData()

        return when (eventName) {
            EventName.ACHIEVE_LEVEL ->
                AchieveLevelEvent(
                    level = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.ADD_PAYMENT_INFO ->
                AddPaymentInfoEvent(
                    paymentInfo = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.ADD_TO_CART ->
                AddToCartEvent(
                    addToCartObject = map.getSerializeObjectOrNull(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.ADD_TO_WISHLIST ->
                AddToWishlistEvent(
                    wishList = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CLICK_ADV ->
                ClickAdvEvent(
                    advertisement = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.COMPLETE_REGISTRATION ->
                CompleteRegistrationEvent(
                    registration = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.COMPLETE_STREAM ->
                CompleteStreamEvent(
                    stream = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.COMPLETE_TRIAL ->
                CompleteTrialEvent(
                    trial = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.COMPLETE_TUTORIAL ->
                CompleteTutorialEvent(
                    tutorial = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CONTENT_ITEMS_VIEW ->
                ContentItemsViewEvent(
                    objects = map.getSerializeArrayOfObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_01 ->
                CustomId01Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_02 ->
                CustomId02Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_03 ->
                CustomId03Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_04 ->
                CustomId04Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_05 ->
                CustomId05Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_06 ->
                CustomId06Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_07 ->
                CustomId07Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_08 ->
                CustomId08Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_09 ->
                CustomId09Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CUSTOM_ID_10 ->
                CustomId10Event(
                    custom = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.DEEP_LINKED ->
                DeepLinkedEvent(
                    map.getSerializeString().toBoolean(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.INITIATE_PURCHASE ->
                InitiatePurchaseEvent(
                    purchaseData = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.INITIATE_STREAM ->
                InitiateStreamEvent(
                    stream = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.INVITE ->
                InviteEvent(
                    invite = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.LAST_ATTRIBUTED_TOUCH -> {
                val touchType = map.getSerializeString(AffiseProperty.TYPE.type).toTouchType()
                    ?: TouchType.CLICK
                val touchData = map.getSerializeObject(AffiseProperty.DATA.type)
                LastAttributedTouchEvent(
                    touchType = touchType,
                    timeStampMillis = timeStamp,
                    touchData = touchData,
                    userData = userData
                )
            }

            EventName.LIST_VIEW ->
                ListViewEvent(
                    list = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.LOGIN ->
                LoginEvent(
                    login = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.OPENED_FROM_PUSH_NOTIFICATION ->
                OpenedFromPushNotificationEvent(
                    details = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.PURCHASE ->
                PurchaseEvent(
                    purchaseData = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.RATE ->
                RateEvent(
                    rate = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.RE_ENGAGE ->
                ReEngageEvent(
                    reEngage = map.getSerializeString(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.RESERVE ->
                ReserveEvent(
                    reserve = map.getSerializeArrayOfObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.SALES ->
                SalesEvent(
                    salesData = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.SEARCH ->
                SearchEvent(
                    search = map.getSerializeArray(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.SHARE ->
                ShareEvent(
                    share = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.SPEND_CREDITS ->
                SpendCreditsEvent(
                    credits = map.getSerializeString().toLongOrNull() ?: 0L,
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.START_REGISTRATION ->
                StartRegistrationEvent(
                    registration = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.START_TRIAL ->
                StartTrialEvent(
                    trial = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.START_TUTORIAL ->
                StartTutorialEvent(
                    tutorial = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.SUBSCRIBE ->
                SubscribeEvent(
                    subscribe = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.TRAVEL_BOOKING ->
                TravelBookingEvent(
                    details = map.getSerializeArray(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.UNLOCK_ACHIEVEMENT ->
                UnlockAchievementEvent(
                    achievement = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.UNSUBSCRIBE ->
                UnsubscribeEvent(
                    unsubscribe = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.UPDATE ->
                UpdateEvent(
                    details = map.getSerializeArray(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.VIEW_ADV ->
                ViewAdvEvent(
                    ad = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.VIEW_CART ->
                ViewCartEvent(
                    objects = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.VIEW_ITEM ->
                ViewItemEvent(
                    item = map.getSerializeObject(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.VIEW_ITEMS ->
                ViewItemsEvent(
                    items = map.getSerializeArray(),
                    timeStampMillis = timeStamp,
                    userData = userData
                )

            EventName.CONTACT ->
                ContactEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOMIZE_PRODUCT ->
                CustomizeProductEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.DONATE ->
                DonateEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.FIND_LOCATION ->
                FindLocationEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.INITIATE_CHECKOUT ->
                InitiateCheckoutEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.LEAD ->
                LeadEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.SCHEDULE ->
                ScheduleEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.SUBMIT_APPLICATION ->
                SubmitApplicationEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.VIEW_CONTENT ->
                ViewContentEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.ORDER ->
                OrderEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.ORDER_CANCEL ->
                OrderCancelEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.ORDER_RETURN_REQUEST ->
                OrderReturnRequestEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.ORDER_RETURN_REQUEST_CANCEL ->
                OrderReturnRequestCancelEvent(userData = userData, timeStampMillis = timeStamp)
        }
    }

    private fun Map<*, *>.getSerializeObjectOrNull(key: String? = null): JSONObject? {
        return (this.getPropertyByKey<Any>(key) as? Map<*, *>)?.toJSONObject()
    }

    private fun Map<*, *>.getSerializeString(key: String? = null): String {
        return this.getPropertyByKey<Any>(key)?.toString() ?: ""
    }

    private fun Map<*, *>.getSerializeObject(key: String? = null): JSONObject {
        return this.getSerializeObjectOrNull(key) ?: JSONObject()
    }

    private fun Map<*, *>.getSerializeArrayOfAny(): List<*> {
        return (this.getPropertyByKey<Any>() as? List<*>) ?: emptyList<Any>()
    }

    private fun Map<*, *>.getSerializeArray(): JSONArray {
        return JSONArray(this.getSerializeArrayOfAny())
    }

    private fun Map<*, *>.getSerializeArrayOfObject(): List<JSONObject> {
        val jsonList = this.getSerializeArray().let { jsonArray ->
            val list = mutableListOf<JSONObject>()
            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i)?.let {
                    list.add(it)
                }
            }
            return@let list
        }

        return jsonList
    }
}