package com.affise.app.ui.fragments.buttons.adapters

import androidx.recyclerview.widget.RecyclerView
import com.affise.app.databinding.ListItemEventBinding
import com.affise.attribution.events.Event
import com.affise.attribution.events.predefined.*
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
            is SalesEvent -> bind(item, item.getName())
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
            is UnsubscriptionEvent -> bind(item, item.subtype)
        }
    }

    private fun bind(item: Event, title: String) {
        binding.eventButton.setOnClickListener {
            onClick.invoke(item)
        }

        binding.eventButton.text = title
    }
}