package com.affise.attribution.internal.events

import com.affise.attribution.events.Event
import com.affise.attribution.events.subscription.ConvertedOfferEvent
import com.affise.attribution.events.subscription.ConvertedOfferFromRetryEvent
import com.affise.attribution.events.subscription.ConvertedTrialEvent
import com.affise.attribution.events.subscription.ConvertedTrialFromRetryEvent
import com.affise.attribution.events.subscription.FailedOfferFromRetryEvent
import com.affise.attribution.events.subscription.FailedOfferiseEvent
import com.affise.attribution.events.subscription.FailedSubscriptionEvent
import com.affise.attribution.events.subscription.FailedSubscriptionFromRetryEvent
import com.affise.attribution.events.subscription.FailedTrialEvent
import com.affise.attribution.events.subscription.FailedTrialFromRetryEvent
import com.affise.attribution.events.subscription.InitialOfferEvent
import com.affise.attribution.events.subscription.InitialSubscriptionEvent
import com.affise.attribution.events.subscription.InitialTrialEvent
import com.affise.attribution.events.subscription.OfferInRetryEvent
import com.affise.attribution.events.subscription.ReactivatedSubscriptionEvent
import com.affise.attribution.events.subscription.RenewedSubscriptionEvent
import com.affise.attribution.events.subscription.RenewedSubscriptionFromRetryEvent
import com.affise.attribution.events.subscription.SubscriptionInRetryEvent
import com.affise.attribution.events.subscription.SubscriptionSubType
import com.affise.attribution.events.subscription.TrialInRetryEvent
import com.affise.attribution.events.subscription.UnsubscriptionEvent
import com.affise.attribution.internal.utils.getEventData
import com.affise.attribution.internal.utils.getUserData
import org.json.JSONObject

internal object SubscriptionEvent {
    fun create(subType: SubscriptionSubType, map: Map<*, *>): Event? {
        val userData = map.getUserData()
        val eventData = map.getEventData() ?: mapOf<Any, Any?>()
        val data = JSONObject(eventData.toMutableMap())

        return when (subType) {
            SubscriptionSubType.AFFISE_SUB_INITIAL_SUBSCRIPTION ->
                InitialSubscriptionEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_INITIAL_TRIAL ->
                InitialTrialEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_INITIAL_OFFER ->
                InitialOfferEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_FAILED_TRIAL ->
                FailedTrialEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_FAILED_OFFERISE ->
                FailedOfferiseEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_FAILED_SUBSCRIPTION ->
                FailedSubscriptionEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_FAILED_TRIAL_FROM_RETRY ->
                FailedTrialFromRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_FAILED_OFFER_FROM_RETRY ->
                FailedOfferFromRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_FAILED_SUBSCRIPTION_FROM_RETRY ->
                FailedSubscriptionFromRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_TRIAL_IN_RETRY ->
                TrialInRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_OFFER_IN_RETRY ->
                OfferInRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_SUBSCRIPTION_IN_RETRY ->
                SubscriptionInRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_CONVERTED_TRIAL ->
                ConvertedTrialEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_CONVERTED_OFFER ->
                ConvertedOfferEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_REACTIVATED_SUBSCRIPTION ->
                ReactivatedSubscriptionEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_RENEWED_SUBSCRIPTION ->
                RenewedSubscriptionEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_CONVERTED_TRIAL_FROM_RETRY ->
                ConvertedTrialFromRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_CONVERTED_OFFER_FROM_RETRY ->
                ConvertedOfferFromRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_RENEWED_SUBSCRIPTION_FROM_RETRY ->
                RenewedSubscriptionFromRetryEvent(data = data, userData = userData)

            SubscriptionSubType.AFFISE_SUB_UNSUBSCRIPTION ->
                UnsubscriptionEvent(data = data, userData = userData)
        }
    }

}