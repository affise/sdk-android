package com.affise.attribution.events

import com.affise.attribution.events.predefined.*
import com.affise.attribution.events.subscription.*
import com.affise.attribution.parameters.Parameters
import org.json.JSONObject

fun JSONObject.classOfEvent(): Class<out Event>? {
    val name = this.optString(Parameters.AFFISE_EVENT_NAME)
    val subtype = this.optJSONObject(Parameters.AFFISE_EVENT_DATA)?.optString(SubscriptionParameters.AFFISE_SUBSCRIPTION_EVENT_TYPE_KEY)
    return when (name) {
        "AchieveLevel" -> AchieveLevelEvent::class.java
        "AddPaymentInfo" -> AddPaymentInfoEvent::class.java
        "AddToCart" -> AddToCartEvent::class.java
        "AddToWishlist" -> AddToWishlistEvent::class.java
        "ClickAdv" -> ClickAdvEvent::class.java
        "CompleteRegistration" -> CompleteRegistrationEvent::class.java
        "CompleteStream" -> CompleteStreamEvent::class.java
        "CompleteTrial" -> CompleteTrialEvent::class.java
        "CompleteTutorial" -> CompleteTutorialEvent::class.java
        "ContentItemsView" -> ContentItemsViewEvent::class.java
        "CustomId01" -> CustomId01Event::class.java
        "CustomId02" -> CustomId02Event::class.java
        "CustomId03" -> CustomId03Event::class.java
        "CustomId04" -> CustomId04Event::class.java
        "CustomId05" -> CustomId05Event::class.java
        "CustomId06" -> CustomId06Event::class.java
        "CustomId07" -> CustomId07Event::class.java
        "CustomId08" -> CustomId08Event::class.java
        "CustomId09" -> CustomId09Event::class.java
        "CustomId10" -> CustomId10Event::class.java
        "DeepLinked" -> DeepLinkedEvent::class.java
        "InitiatePurchase" -> InitiatePurchaseEvent::class.java
        "InitiateStream" -> InitiateStreamEvent::class.java
        "Invite" -> InviteEvent::class.java
        "LastAttributedTouch" -> LastAttributedTouchEvent::class.java
        "ListView" -> ListViewEvent::class.java
        "Login" -> LoginEvent::class.java
        "OpenedFromPushNotification" -> OpenedFromPushNotificationEvent::class.java
        "Purchase" -> PurchaseEvent::class.java
        "Rate" -> RateEvent::class.java
        "ReEngage" -> ReEngageEvent::class.java
        "Reserve" -> ReserveEvent::class.java
        "Sales" -> SalesEvent::class.java
        "Search" -> SearchEvent::class.java
        "Share" -> ShareEvent::class.java
        "SpendCredits" -> SpendCreditsEvent::class.java
        "StartRegistration" -> StartRegistrationEvent::class.java
        "StartTrial" -> StartTrialEvent::class.java
        "StartTutorial" -> StartTutorialEvent::class.java
        "Subscribe" -> SubscribeEvent::class.java
        "TravelBooking" -> TravelBookingEvent::class.java
        "UnlockAchievement" -> UnlockAchievementEvent::class.java
        "Unsubscribe" -> UnsubscribeEvent::class.java
        "Update" -> UpdateEvent::class.java
        "ViewAdv" -> ViewAdvEvent::class.java
        "ViewCart" -> ViewCartEvent::class.java
        "ViewItem" -> ViewItemEvent::class.java
        "ViewItems" -> ViewItemsEvent::class.java
        SubscriptionParameters.AFFISE_UNSUBSCRIPTION,
        SubscriptionParameters.AFFISE_SUBSCRIPTION_ACTIVATION,
        SubscriptionParameters.AFFISE_SUBSCRIPTION_CANCELLATION,
        SubscriptionParameters.AFFISE_SUBSCRIPTION_ENTERED_BILLING_RETRY,
        SubscriptionParameters.AFFISE_SUBSCRIPTION_FIRST_CONVERSION,
        SubscriptionParameters.AFFISE_SUBSCRIPTION_REACTIVATION,
        SubscriptionParameters.AFFISE_SUBSCRIPTION_RENEWAL,
        SubscriptionParameters.AFFISE_SUBSCRIPTION_RENEWAL_FROM_BILLING_RETRY -> subscriptionEventClass(subtype)
        else -> null
    }
}

private fun subscriptionEventClass(subtype: String?): Class<out BaseSubscriptionEvent>? {
    return when (subtype) {
        SubscriptionParameters.AFFISE_SUB_INITIAL_SUBSCRIPTION -> InitialSubscriptionEvent::class.java
        SubscriptionParameters.AFFISE_SUB_INITIAL_TRIAL -> InitialTrialEvent::class.java
        SubscriptionParameters.AFFISE_SUB_INITIAL_OFFER -> InitialOfferEvent::class.java
        SubscriptionParameters.AFFISE_SUB_FAILED_TRIAL -> FailedTrialEvent::class.java
        SubscriptionParameters.AFFISE_SUB_FAILED_OFFERISE -> FailedOfferiseEvent::class.java
        SubscriptionParameters.AFFISE_SUB_FAILED_SUBSCRIPTION -> FailedSubscriptionEvent::class.java
        SubscriptionParameters.AFFISE_SUB_FAILED_TRIAL_FROM_RETRY -> FailedTrialFromRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_FAILED_OFFER_FROM_RETRY -> FailedOfferFromRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_FAILED_SUBSCRIPTION_FROM_RETRY -> FailedSubscriptionFromRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_TRIAL_IN_RETRY -> TrialInRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_OFFER_IN_RETRY -> OfferInRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_SUBSCRIPTION_IN_RETRY -> SubscriptionInRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_CONVERTED_TRIAL -> ConvertedTrialEvent::class.java
        SubscriptionParameters.AFFISE_SUB_CONVERTED_OFFER -> ConvertedOfferEvent::class.java
        SubscriptionParameters.AFFISE_SUB_REACTIVATED_SUBSCRIPTION -> ReactivatedSubscriptionEvent::class.java
        SubscriptionParameters.AFFISE_SUB_RENEWED_SUBSCRIPTION -> RenewedSubscriptionEvent::class.java
        SubscriptionParameters.AFFISE_SUB_CONVERTED_TRIAL_FROM_RETRY -> ConvertedTrialFromRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_CONVERTED_OFFER_FROM_RETRY -> ConvertedOfferFromRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_RENEWED_SUBSCRIPTION_FROM_RETRY -> RenewedSubscriptionFromRetryEvent::class.java
        SubscriptionParameters.AFFISE_SUB_UNSUBSCRIPTION -> UnsubscriptionEvent::class.java
        else -> null
    }
}