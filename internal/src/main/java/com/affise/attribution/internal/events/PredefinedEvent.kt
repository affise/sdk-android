package com.affise.attribution.internal.events

import android.util.Log
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
import com.affise.attribution.events.predefined.TravelBookingEvent
import com.affise.attribution.events.predefined.UnlockAchievementEvent
import com.affise.attribution.events.predefined.UnsubscribeEvent
import com.affise.attribution.events.predefined.UpdateEvent
import com.affise.attribution.events.predefined.ViewAdvEvent
import com.affise.attribution.events.predefined.ViewCartEvent
import com.affise.attribution.events.predefined.ViewContentEvent
import com.affise.attribution.events.predefined.ViewItemEvent
import com.affise.attribution.events.predefined.ViewItemsEvent
import com.affise.attribution.internal.utils.getTimeStamp
import com.affise.attribution.internal.utils.getUserData
import com.affise.attribution.utils.timestamp

internal object PredefinedEvent {

    fun create(name: String?, map: Map<*, *>): Event? {
        val eventName = EventName.from(name)
        eventName ?: return null
        val timeStamp = map.getTimeStamp() ?: timestamp()
        val userData = map.getUserData()

        return when (eventName) {
            EventName.ACHIEVE_LEVEL ->
                AchieveLevelEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.ADD_PAYMENT_INFO ->
                AddPaymentInfoEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.ADD_TO_CART ->
                AddToCartEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.ADD_TO_WISHLIST ->
                AddToWishlistEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.CLICK_ADV ->
                ClickAdvEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.COMPLETE_REGISTRATION ->
                CompleteRegistrationEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.COMPLETE_STREAM ->
                CompleteStreamEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.COMPLETE_TRIAL ->
                CompleteTrialEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.COMPLETE_TUTORIAL ->
                CompleteTutorialEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.CONTENT_ITEMS_VIEW ->
                ContentItemsViewEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_01 ->
                CustomId01Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_02 ->
                CustomId02Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_03 ->
                CustomId03Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_04 ->
                CustomId04Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_05 ->
                CustomId05Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_06 ->
                CustomId06Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_07 ->
                CustomId07Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_08 ->
                CustomId08Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_09 ->
                CustomId09Event(userData = userData, timeStampMillis = timeStamp)

            EventName.CUSTOM_ID_10 ->
                CustomId10Event(userData = userData, timeStampMillis = timeStamp)

            EventName.DEEP_LINKED ->
                DeepLinkedEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.INITIATE_PURCHASE ->
                InitiatePurchaseEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.INITIATE_STREAM ->
                InitiateStreamEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.INVITE ->
                InviteEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.LAST_ATTRIBUTED_TOUCH ->
                LastAttributedTouchEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.LIST_VIEW ->
                ListViewEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.LOGIN ->
                LoginEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.OPENED_FROM_PUSH_NOTIFICATION ->
                OpenedFromPushNotificationEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.PURCHASE ->
                PurchaseEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.RATE ->
                RateEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.RE_ENGAGE ->
                ReEngageEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.RESERVE ->
                ReserveEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.SALES ->
                SalesEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.SEARCH ->
                SearchEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.SHARE ->
                ShareEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.SPEND_CREDITS ->
                SpendCreditsEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.START_REGISTRATION ->
                StartRegistrationEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.START_TRIAL ->
                StartTrialEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.START_TUTORIAL ->
                StartTutorialEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.SUBSCRIBE ->
                SubscribeEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.TRAVEL_BOOKING ->
                TravelBookingEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.UNLOCK_ACHIEVEMENT ->
                UnlockAchievementEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.UNSUBSCRIBE ->
                UnsubscribeEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.UPDATE ->
                UpdateEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.VIEW_ADV ->
                ViewAdvEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.VIEW_CART ->
                ViewCartEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.VIEW_ITEM ->
                ViewItemEvent(userData = userData, timeStampMillis = timeStamp)

            EventName.VIEW_ITEMS ->
                ViewItemsEvent(userData = userData, timeStampMillis = timeStamp)

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
}