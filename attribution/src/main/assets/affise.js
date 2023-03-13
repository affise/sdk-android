var Affise = class {
  static sendEvent(event) {
    AffiseBridge.sendEvent(JSON.stringify(event));
  }
};

class PredefinedParameters{
    constructor(name, value) {
        this.name = name;
        this.value = value;
    }
}

class Event {
    constructor(name) {
        this.affise_event_id = this._generateUUID();
        this.affise_event_name = name;
        this.affise_event_category = 'web';
        this.affise_event_timestamp = Date.now();
    }

    addPredefinedParameter(key, value) {
        if (typeof this.affise_parameters === 'undefined') {
            this.affise_parameters = {};
        }
        this.affise_parameters[key] = value;
    }

    _generateUUID() {
        var d = new Date().getTime();
        var d2 = ((typeof performance !== 'undefined') && performance.now && (performance.now()*1000)) || 0;
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16;
            if(d > 0){
                r = (d + r)%16 | 0;
                d = Math.floor(d/16);
            } else {
                r = (d2 + r)%16 | 0;
                d2 = Math.floor(d2/16);
            }
            return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
        });
    }
}

class SubscriptionEvent extends Event {
    constructor(data, userData, subscriptionKey, subscriptionSubKey) {
        super(subscriptionKey);

        var event_data = {affise_event_type:subscriptionSubKey};

        for(var key in data){
           event_data[key] = data[key];
        }

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = event_data;
    }
}

class AchieveLevelEvent extends Event {
    constructor(level, timeStampMillis, userData) {
        super('AchieveLevel');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_achieve_level: level,
            affise_event_achieve_level_timestamp: timeStampMillis
        };
    }
}

class AddPaymentInfoEvent extends Event {
    constructor(paymentInfo, timeStampMillis, userData) {
        super('AddPaymentInfo');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_add_payment_info: paymentInfo,
            affise_event_add_payment_info_timestamp: timeStampMillis
        };
    }
}

class AddToCartEvent extends Event {
    constructor(addToCartObject, timeStampMillis, userData) {
        super('AddToCart');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_add_to_cart: addToCartObject,
            affise_event_add_to_cart_timestamp: timeStampMillis
        };
    }
}

class AddToWishlistEvent extends Event {
    constructor(wishList, timeStampMillis, userData) {
        super('AddToWishlist');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_add_to_wishlist: wishList,
            affise_event_add_to_wishlist_timestamp: timeStampMillis
        };
    }
}

class ClickAdvEvent extends Event {
    constructor(advertisement, timeStampMillis, userData) {
        super('ClickAdv');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_click_adv: advertisement,
            affise_event_click_adv_timestamp: timeStampMillis
        };
    }
}

class CompleteRegistrationEvent extends Event {
    constructor(registration, timeStampMillis, userData) {
        super('CompleteRegistration');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_complete_registration: registration,
            affise_event_complete_registration_timestamp: timeStampMillis
        };
    }
}

class CompleteStreamEvent extends Event {
    constructor(stream, timeStampMillis, userData) {
        super('CompleteStream');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_complete_stream: stream,
            affise_event_complete_stream_timestamp: timeStampMillis
        };
    }
}

class CompleteTrialEvent extends Event {
    constructor(trial, timeStampMillis, userData) {
        super('CompleteTrial');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_complete_trial: trial,
            affise_event_complete_trial_timestamp: timeStampMillis
        };
    }
}

class CompleteTutorialEvent extends Event {
    constructor(tutorial, timeStampMillis, userData) {
        super('CompleteTutorial');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_complete_tutorial: tutorial,
            affise_event_complete_tutorial_timestamp: timeStampMillis
        };
    }
}

class ContentItemsViewEvent extends Event {
    constructor(objects, userData) {
        super('ContentItemsView');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_content_items_view: objects
        };
    }
}

class CustomId01Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId01');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_01: custom,
            affise_event_custom_id_01_timestamp: timeStampMillis
        };
    }
}

class CustomId02Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId02');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_02: custom,
            affise_event_custom_id_02_timestamp: timeStampMillis
        };
    }
}

class CustomId03Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId03');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_03: custom,
            affise_event_custom_id_03_timestamp: timeStampMillis
        };
    }
}

class CustomId04Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId04');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_04: custom,
            affise_event_custom_id_04_timestamp: timeStampMillis
        };
    }
}

class CustomId05Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId05');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_05: custom,
            affise_event_custom_id_05_timestamp: timeStampMillis
        };
    }
}

class CustomId06Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId06');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_06: custom,
            affise_event_custom_id_06_timestamp: timeStampMillis
        };
    }
}

class CustomId07Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId07');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_07: custom,
            affise_event_custom_id_07_timestamp: timeStampMillis
        };
    }
}

class CustomId08Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId08');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_08: custom,
            affise_event_custom_id_08_timestamp: timeStampMillis
        };
    }
}

class CustomId09Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId09');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_09: custom,
            affise_event_custom_id_09_timestamp: timeStampMillis
        };
    }
}

class CustomId10Event extends Event {
    constructor(custom, timeStampMillis, userData) {
        super('CustomId10');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_custom_id_10: custom,
            affise_event_custom_id_10_timestamp: timeStampMillis
        };
    }
}

class DeepLinkedEvent extends Event {
    constructor(isLinked, userData) {
        super('DeepLinked');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_deep_linked: isLinked
        };
    }
}

class InitiatePurchaseEvent extends Event {
    constructor(purchaseData, timeStampMillis, userData) {
        super('InitiatePurchase');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_initiate_purchase: purchaseData,
            affise_event_initiate_purchase_timestamp: timeStampMillis
        };
    }
}

class InitiateStreamEvent extends Event {
    constructor(stream, timeStampMillis, userData) {
        super('InitiateStream');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_initiate_stream: stream,
            affise_event_initiate_stream_timestamp: timeStampMillis
        };
    }
}

class InviteEvent extends Event {
    constructor(invite, timeStampMillis, userData) {
        super('Invite');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_invite: invite,
            affise_event_invite_timestamp: timeStampMillis
        };
    }
}

class LastAttributedTouchEvent extends Event {
    constructor(touchType, timeStampMillis, touchData, userData) {
        super('LastAttributedTouch');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_last_attributed_touch_type: touchType,
            affise_event_last_attributed_touch_timestamp: timeStampMillis,
            affise_event_last_attributed_touch_data: touchData
        };
    }
}

class ListViewEvent extends Event {
    constructor(list, userData) {
        super('ListView');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_list_view: list
        };
    }
}

class LoginEvent extends Event {
    constructor(login, timeStampMillis, userData) {
        super('Login');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_login: login,
            affise_event_login_timestamp: timeStampMillis
        };
    }
}

class OpenedFromPushNotificationEvent extends Event {
    constructor(details, userData) {
        super('OpenedFromPushNotification');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_opened_from_push_notification: details
        };
    }
}

class PurchaseEvent extends Event {
    constructor(purchaseData, timeStampMillis, userData) {
        super('Purchase');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_purchase: purchaseData,
            affise_event_purchase_timestamp: timeStampMillis
        };
    }
}

class RateEvent extends Event {
    constructor(rate, timeStampMillis, userData) {
        super('Rate');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_rate: rate,
            affise_event_rate_timestamp: timeStampMillis
        };
    }
}

class ReEngageEvent extends Event {
    constructor(reEngage, userData) {
        super('ReEngage');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_re_engage: reEngage
        };
    }
}

class ReserveEvent extends Event {
    constructor(reserve, timeStampMillis, userData) {
        super('Reserve');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_reserve: reserve,
            affise_event_reserve_timestamp: timeStampMillis
        };
    }
}

class SalesEvent extends Event {
    constructor(sales, timeStampMillis, userData) {
        super('Sales');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_sales: sales,
            affise_event_sales_timestamp: timeStampMillis
        };
    }
}

class SearchEvent extends Event {
    constructor(search, timeStampMillis, userData) {
        super('Search');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_search: search,
            affise_event_search_timestamp: timeStampMillis
        };
    }
}

class ShareEvent extends Event {
    constructor(share, timeStampMillis, userData) {
        super('Share');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_share: share,
            affise_event_share_timestamp: timeStampMillis
        };
    }
}

class SpendCreditsEvent extends Event {
    constructor(credits, timeStampMillis, userData) {
        super('SpendCredits');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_spend_credits: credits,
            affise_event_spend_credits_timestamp: timeStampMillis
        };
    }
}

class StartRegistrationEvent extends Event {
    constructor(registration, timeStampMillis, userData) {
        super('StartRegistration');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_start_registration: registration,
            affise_event_start_registration_timestamp: timeStampMillis
        };
    }
}

class StartTrialEvent extends Event {
    constructor(trial, timeStampMillis, userData) {
        super('StartTrial');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_start_trial: trial,
            affise_event_start_trial_timestamp: timeStampMillis
        };
    }
}

class StartTutorialEvent extends Event {
    constructor(tutorial, timeStampMillis, userData) {
        super('StartTutorial');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_start_tutorial: tutorial,
            affise_event_start_tutorial_timestamp: timeStampMillis
        };
    }
}

class SubscribeEvent extends Event {
    constructor(tutorial, timeStampMillis, userData) {
        super('Subscribe');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_subscribe: tutorial,
            affise_event_subscribe_timestamp: timeStampMillis
        };
    }
}

class TravelBookingEvent extends Event {
    constructor(details, userData) {
        super('TravelBooking');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_travel_booking: details
        };
    }
}

class UnlockAchievementEvent extends Event {
    constructor(achievement, timeStampMillis, userData) {
        super('UnlockAchievement');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_unlock_achievement: achievement,
            affise_event_unlock_achievement_timestamp: timeStampMillis
        };
    }
}

class UnsubscribeEvent extends Event {
    constructor(unsubscribe, timeStampMillis, userData) {
        super('Unsubscribe');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_unsubscribe: unsubscribe,
            affise_event_unsubscribe_timestamp: timeStampMillis
        };
    }
}

class UpdateEvent extends Event {
    constructor(details, userData) {
        super('Update');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_update: details
        };
    }
}

class ViewAdvEvent extends Event {
    constructor(ad, timeStampMillis, userData) {
        super('ViewAdv');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_view_adv: ad,
            affise_event_view_adv_timestamp: timeStampMillis
        };
    }
}

class ViewCartEvent extends Event {
    constructor(objects, userData) {
        super('ViewCart');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_view_cart: objects
        };
    }
}

class ViewItemEvent extends Event {
    constructor(item, userData) {
        super('ViewItem');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_view_item: item
        };
    }
}

class ViewItemsEvent extends Event {
    constructor(items, userData) {
        super('ViewItems');

        this.affise_event_first_for_user = false;
        this.affise_event_user_data = userData;
        this.affise_event_data = {
            affise_event_view_items: items
        };
    }
}

class InitialSubscriptionEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_activation',
            'affise_sub_initial_subscription'
        );
    }
}

class InitialTrialEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_activation',
            'affise_sub_initial_trial'
        );
    }
}

class InitialOfferEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_activation',
            'affise_sub_initial_offer'
        );
    }
}

class ConvertedTrialEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_first_conversion',
            'affise_sub_converted_trial'
        );
    }
}

class ConvertedOfferEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_first_conversion',
            'affise_sub_converted_offer'
        );
    }
}

class TrialInRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_entered_billing_retry',
            'affise_sub_trial_in_retry'
        );
    }
}

class OfferInRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_entered_billing_retry',
            'affise_sub_offer_in_retry'
        );
    }
}

class SubscriptionInRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_entered_billing_retry',
            'affise_sub_subscription_in_retry'
        );
    }
}

class RenewedSubscriptionEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_renewal',
            'affise_sub_renewed_subscription'
        );
    }
}

class FailedSubscriptionFromRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_cancellation',
            'affise_sub_failed_subscription_from_retry'
        );
    }
}

class FailedOfferFromRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_cancellation',
            'affise_sub_failed_offer_from_retry'
        );
    }
}

class FailedTrialFromRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_cancellation',
            'affise_sub_failed_trial_from_retry'
        );
    }
}

class FailedSubscriptionEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_cancellation',
            'affise_sub_failed_subscription'
        );
    }
}

class FailedOfferiseEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_cancellation',
            'affise_sub_failed_offer'
        );
    }
}

class FailedTrialEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_cancellation',
            'affise_sub_failed_trial'
        );
    }
}

class ReactivatedSubscriptionEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_reactivation',
            'affise_sub_reactivated_subscription'
        );
    }
}

class RenewedSubscriptionFromRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_renewal_from_billing_retry',
            'affise_sub_renewed_subscription_from_retry'
        );
    }
}

class ConvertedOfferFromRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_renewal_from_billing_retry',
            'affise_sub_converted_offer_from_retry'
        );
    }
}

class ConvertedTrialFromRetryEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_subscription_renewal_from_billing_retry',
            'affise_sub_converted_trial_from_retry'
        );
    }
}

class UnsubscriptionEvent extends SubscriptionEvent {
    constructor(data, userData) {
        super(
            data,
            userData,
            'affise_unsubscription',
            'affise_sub_unsubscription'
        );
    }
}