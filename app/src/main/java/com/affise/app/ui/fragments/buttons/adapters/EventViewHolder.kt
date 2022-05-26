package com.affise.app.ui.fragments.buttons.adapters

import androidx.recyclerview.widget.RecyclerView
import com.affise.app.databinding.ListItemEventBinding
import com.affise.attribution.events.Event
import com.affise.attribution.events.predefined.AchieveLevelEvent
import com.affise.attribution.events.predefined.AddPaymentInfoEvent
import com.affise.attribution.events.predefined.AddToCartEvent
import com.affise.attribution.events.predefined.AddToWishlistEvent
import com.affise.attribution.events.predefined.ClickAdvEvent
import com.affise.attribution.events.predefined.CompleteRegistrationEvent
import com.affise.attribution.events.predefined.CompleteStreamEvent
import com.affise.attribution.events.predefined.CompleteTrialEvent
import com.affise.attribution.events.predefined.CompleteTutorialEvent
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
import com.affise.attribution.events.predefined.DeepLinkedEvent
import com.affise.attribution.events.predefined.InitiatePurchaseEvent
import com.affise.attribution.events.predefined.InitiateStreamEvent
import com.affise.attribution.events.predefined.InviteEvent
import com.affise.attribution.events.predefined.LastAttributedTouchEvent
import com.affise.attribution.events.predefined.ListViewEvent
import com.affise.attribution.events.predefined.LoginEvent
import com.affise.attribution.events.predefined.OpenedFromPushNotificationEvent
import com.affise.attribution.events.predefined.PurchaseEvent
import com.affise.attribution.events.predefined.RateEvent
import com.affise.attribution.events.predefined.ReEngageEvent
import com.affise.attribution.events.predefined.ReserveEvent
import com.affise.attribution.events.predefined.SearchEvent
import com.affise.attribution.events.predefined.ShareEvent
import com.affise.attribution.events.predefined.SpendCreditsEvent
import com.affise.attribution.events.predefined.StartRegistrationEvent
import com.affise.attribution.events.predefined.StartTrialEvent
import com.affise.attribution.events.predefined.StartTutorialEvent
import com.affise.attribution.events.predefined.SubscribeEvent
import com.affise.attribution.events.predefined.TravelBookingEvent
import com.affise.attribution.events.predefined.UnlockAchievementEvent
import com.affise.attribution.events.predefined.UnsubscribeEvent
import com.affise.attribution.events.predefined.UpdateEvent
import com.affise.attribution.events.predefined.ViewAdvEvent
import com.affise.attribution.events.predefined.ViewCartEvent
import com.affise.attribution.events.predefined.ViewItemEvent
import com.affise.attribution.events.predefined.ViewItemsEvent
import com.affise.attribution.events.subscription.*

class EventViewHolder(
    private val binding: ListItemEventBinding,
    private val onClick: (Event) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Event) {
        when (item) {
            is AchieveLevelEvent -> bind(item, item.getName())
            is AddPaymentInfoEvent -> bind(item, item.getName())
            is AddToCartEvent -> bind(item, item.getName())
            is AddToWishlistEvent -> bind(item, item.getName())
            is ClickAdvEvent -> bind(item, item.getName())
            is CompleteRegistrationEvent -> bind(item, item.getName())
            is CompleteStreamEvent -> bind(item, item.getName())
            is CompleteTrialEvent -> bind(item, item.getName())
            is CompleteTutorialEvent -> bind(item, item.getName())
            is ContentItemsViewEvent -> bind(item, item.getName())
            is CustomId01Event -> bind(item, item.getName())
            is CustomId02Event -> bind(item, item.getName())
            is CustomId03Event -> bind(item, item.getName())
            is CustomId04Event -> bind(item, item.getName())
            is CustomId05Event -> bind(item, item.getName())
            is CustomId06Event -> bind(item, item.getName())
            is CustomId07Event -> bind(item, item.getName())
            is CustomId08Event -> bind(item, item.getName())
            is CustomId09Event -> bind(item, item.getName())
            is CustomId10Event -> bind(item, item.getName())
            is DeepLinkedEvent -> bind(item, item.getName())
            is InitiatePurchaseEvent -> bind(item, item.getName())
            is InitiateStreamEvent -> bind(item, item.getName())
            is InviteEvent -> bind(item, item.getName())
            is LastAttributedTouchEvent -> bind(item, item.getName())
            is ListViewEvent -> bind(item, item.getName())
            is LoginEvent -> bind(item, item.getName())
            is OpenedFromPushNotificationEvent -> bind(item, item.getName())
            is PurchaseEvent -> bind(item, item.getName())
            is RateEvent -> bind(item, item.getName())
            is ReEngageEvent -> bind(item, item.getName())
            is ReserveEvent -> bind(item, item.getName())
            is SearchEvent -> bind(item, item.getName())
            is ShareEvent -> bind(item, item.getName())
            is SpendCreditsEvent -> bind(item, item.getName())
            is StartRegistrationEvent -> bind(item, item.getName())
            is StartTrialEvent -> bind(item, item.getName())
            is StartTutorialEvent -> bind(item, item.getName())
            is SubscribeEvent -> bind(item, item.getName())
            is TravelBookingEvent -> bind(item, item.getName())
            is UnlockAchievementEvent -> bind(item, item.getName())
            is UnsubscribeEvent -> bind(item, item.getName())
            is UpdateEvent -> bind(item, item.getName())
            is ViewAdvEvent -> bind(item, item.getName())
            is ViewCartEvent -> bind(item, item.getName())
            is ViewItemEvent -> bind(item, item.getName())
            is ViewItemsEvent -> bind(item, item.getName())

            is InitialSubscriptionEvent -> bind(item, item.subtype)
            is InitialTrialEvent -> bind(item, item.subtype)
            is InitialOfferEvent -> bind(item, item.subtype)
            is ConvertedTrialEvent -> bind(item, item.subtype)
            is ConvertedOfferEvent -> bind(item, item.subtype)
            is TrialInRetryEvent -> bind(item, item.subtype)
            is OfferInRetryEvent -> bind(item, item.subtype)
            is SubscriptionInRetryEvent -> bind(item, item.subtype)
            is RenewedSubscriptionEvent -> bind(item, item.subtype)
            is FailedSubscriptionFromRetryEvent -> bind(item, item.subtype)
            is FailedOfferFromRetryEvent -> bind(item, item.subtype)
            is FailedTrialFromRetryEvent -> bind(item, item.subtype)
            is FailedSubscriptionEvent -> bind(item, item.subtype)
            is FailedOfferiseEvent -> bind(item, item.subtype)
            is FailedTrialEvent -> bind(item, item.subtype)
            is ReactivatedSubscriptionEvent -> bind(item, item.subtype)
            is RenewedSubscriptionFromRetryEvent -> bind(item, item.subtype)
            is ConvertedOfferFromRetryEvent -> bind(item, item.subtype)
            is ConvertedTrialFromRetryEvent -> bind(item, item.subtype)
        }
    }

    private fun bind(item: Event, title: String) {
        binding.eventButton.setOnClickListener {
            onClick.invoke(item)
        }

        binding.eventButton.text = title
    }
}