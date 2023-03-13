package com.affise.app.ui.fragments.buttons.factories

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
import com.affise.attribution.events.predefined.PredefinedParameters
import com.affise.attribution.events.predefined.PurchaseEvent
import com.affise.attribution.events.predefined.RateEvent
import com.affise.attribution.events.predefined.ReEngageEvent
import com.affise.attribution.events.predefined.ReserveEvent
import com.affise.attribution.events.predefined.SalesEvent
import com.affise.attribution.events.predefined.SearchEvent
import com.affise.attribution.events.predefined.ShareEvent
import com.affise.attribution.events.predefined.SpendCreditsEvent
import com.affise.attribution.events.predefined.StartRegistrationEvent
import com.affise.attribution.events.predefined.StartTrialEvent
import com.affise.attribution.events.predefined.StartTutorialEvent
import com.affise.attribution.events.predefined.SubscribeEvent
import com.affise.attribution.events.predefined.TouchType
import com.affise.attribution.events.predefined.TravelBookingEvent
import com.affise.attribution.events.predefined.UnlockAchievementEvent
import com.affise.attribution.events.predefined.UnsubscribeEvent
import com.affise.attribution.events.predefined.UpdateEvent
import com.affise.attribution.events.predefined.ViewAdvEvent
import com.affise.attribution.events.predefined.ViewCartEvent
import com.affise.attribution.events.predefined.ViewItemEvent
import com.affise.attribution.events.predefined.ViewItemsEvent
import com.affise.attribution.events.subscription.*
import org.json.JSONArray
import org.json.JSONObject

class DefaultEventsFactory : EventsFactory {
    override fun createEvents(): List<Event> {
        return listOf(
            createAchieveLevelEvent(),
            createAddPaymentInfoEvent(),
            createAddToCartEvent(),
            createAddToWishlistEvent(),
            createClickAdvEvent(),
            createCompleteRegistrationEvent(),
            createCompleteStreamEvent(),
            createCompleteTrialEvent(),
            createCompleteTutorialEvent(),
            createContentItemsViewEvent(),
            createCustomId01Event(),
            createCustomId02Event(),
            createCustomId03Event(),
            createCustomId04Event(),
            createCustomId05Event(),
            createCustomId06Event(),
            createCustomId07Event(),
            createCustomId08Event(),
            createCustomId09Event(),
            createCustomId10Event(),
            createDeepLinkedEvent(),
            createInitiatePurchaseEvent(),
            createInitiateStreamEvent(),
            createInviteEvent(),
            createLastAttributedTouchEvent(),
            createListViewEvent(),
            createLoginEvent(),
            createOpenedFromPushNotificationEvent(),
            createPurchaseEvent(),
            createRateEvent(),
            createReEngageEvent(),
            createReserveEvent(),
            createSalesEvent(),
            createSearchEvent(),
            createShareEvent(),
            createSpendCreditsEvent(),
            createStartRegistrationEvent(),
            createStartTrialEvent(),
            createStartTutorialEvent(),
            createSubscribeEvent(),
            createTravelBookingEvent(),
            createUnlockAchievementEvent(),
            createUnsubscribeEvent(),
            createUpdateEvent(),
            createViewAdvEvent(),
            createViewCartEvent(),
            createViewItemEvent(),
            createViewItemsEvent(),
            createInitialSubscriptionEvent(),
            createInitialTrialEvent(),
            createInitialOfferEvent(),
            createConvertedTrialEvent(),
            createConvertedOfferEvent(),
            createTrialInRetryEvent(),
            createOfferInRetryEvent(),
            createSubscriptionInRetryEvent(),
            createRenewedSubscriptionEvent(),
            createFailedSubscriptionFromRetryEvent(),
            createFailedOfferFromRetryEvent(),
            createFailedTrialFromRetryEvent(),
            createFailedSubscriptionEvent(),
            createFailedOfferiseEvent(),
            createFailedTrialEvent(),
            createReactivatedSubscriptionEvent(),
            createRenewedSubscriptionFromRetryEvent(),
            createConvertedOfferFromRetryEvent(),
            createConvertedTrialFromRetryEvent(),
            createUnsubscriptionEvent()
        )
    }

    private fun createUnsubscriptionEvent() = UnsubscriptionEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Unsubscription"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createConvertedTrialFromRetryEvent() = ConvertedTrialFromRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createConvertedOfferFromRetryEvent() = ConvertedOfferFromRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createRenewedSubscriptionFromRetryEvent() = RenewedSubscriptionFromRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createReactivatedSubscriptionEvent() = ReactivatedSubscriptionEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createFailedTrialEvent() = FailedTrialEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createFailedOfferiseEvent() = FailedOfferiseEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createFailedSubscriptionEvent() = FailedSubscriptionEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createFailedTrialFromRetryEvent() = FailedTrialFromRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createFailedOfferFromRetryEvent() = FailedOfferFromRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createFailedSubscriptionFromRetryEvent() = FailedSubscriptionFromRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createRenewedSubscriptionEvent() = RenewedSubscriptionEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createTrialInRetryEvent() = TrialInRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createOfferInRetryEvent() = OfferInRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createSubscriptionInRetryEvent() = SubscriptionInRetryEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createInitialOfferEvent() = InitialOfferEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createInitialTrialEvent() = InitialTrialEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createInitialSubscriptionEvent() = InitialSubscriptionEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createConvertedTrialEvent() = ConvertedTrialEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createConvertedOfferEvent() = ConvertedOfferEvent(
        JSONObject().apply {
            put("affise_event_revenue", 2.99)
            put("affise_event_currency", "USD")
            put("affise_event_product_id", 278459628375)
        },
        "Subscription Plus"
    ).apply {
        addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
    }

    private fun createAchieveLevelEvent(): Event {
        val level = JSONObject().apply {
            put("old_level", 69)
            put("new_level", 70)
        }
        return AchieveLevelEvent(level, System.currentTimeMillis(), "warlock").apply {
            addPredefinedParameter(PredefinedParameters.DEEP_LINK, "https://new-game.lt")
            addPredefinedParameter(PredefinedParameters.SCORE, "25013")
            addPredefinedParameter(PredefinedParameters.LEVEL, "70")
            addPredefinedParameter(PredefinedParameters.SUCCESS, "true")
            addPredefinedParameter(PredefinedParameters.TUTORIAL_ID, "12")
        }
    }

    private fun createAddPaymentInfoEvent(): Event {
        val data = JSONObject().apply {
            put("card", 4138)
            put("type", "phone")
        }
        return AddPaymentInfoEvent(data, System.currentTimeMillis(), "taxi").apply {
            addPredefinedParameter(PredefinedParameters.PURCHASE_CURRENCY, "USD")
        }
    }

    private fun createAddToCartEvent(): Event {
        val products =
            listOf("milk", "cookies", "meat", "vegetables").shuffled().take(1).joinToString()
        val items = JSONObject().apply {
            put("items", products)
        }
        return AddToCartEvent(items, System.currentTimeMillis()).apply {
            addPredefinedParameter(PredefinedParameters.DESCRIPTION, "best before 2029")
        }
    }

    private fun createAddToWishlistEvent(): Event {
        val wishes = listOf("car", "house", "sdk").shuffled().take(1).joinToString()
        val items = JSONObject().apply {
            put("items", wishes)
        }
        return AddToWishlistEvent(items, System.currentTimeMillis(), "next year").apply {
            addPredefinedParameter(PredefinedParameters.COUNTRY, "Russia")
            addPredefinedParameter(PredefinedParameters.CITY, "Voronezh")
            addPredefinedParameter(PredefinedParameters.LAT, "42")
            addPredefinedParameter(PredefinedParameters.LONG, "24")
        }
    }

    private fun createClickAdvEvent(): Event {
        return ClickAdvEvent("facebook-meta", System.currentTimeMillis(), "header").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "PARAM_01")
            addPredefinedParameter(PredefinedParameters.PARAM_02, "PARAM_02")
            addPredefinedParameter(PredefinedParameters.PARAM_03, "PARAM_03")
            addPredefinedParameter(PredefinedParameters.PARAM_04, "PARAM_04")
            addPredefinedParameter(PredefinedParameters.PARAM_05, "PARAM_05")
            addPredefinedParameter(PredefinedParameters.PARAM_06, "PARAM_06")
            addPredefinedParameter(PredefinedParameters.PARAM_07, "PARAM_07")
            addPredefinedParameter(PredefinedParameters.PARAM_08, "PARAM_08")
            addPredefinedParameter(PredefinedParameters.PARAM_09, "PARAM_09")
            addPredefinedParameter(PredefinedParameters.PARAM_10, "PARAM_10")
        }
    }

    private fun createCompleteRegistrationEvent(): Event {
        val data = JSONObject().apply {
            put("email", "dog@gmail.com")
        }
        return CompleteRegistrationEvent(data, System.currentTimeMillis(), "promo").apply {
            addPredefinedParameter(PredefinedParameters.VALIDATED, "02.11.2021")
            addPredefinedParameter(PredefinedParameters.REVIEW_TEXT, "approve")
            addPredefinedParameter(PredefinedParameters.CUSTOMER_SEGMENT, "DOG")
        }
    }

    private fun createCompleteStreamEvent(): Event {
        val data = JSONObject().apply {
            put("streamer", "cat")
            put("max_viewers", "29")
        }
        return CompleteStreamEvent(data, System.currentTimeMillis(), "23 hours").apply {
            addPredefinedParameter(PredefinedParameters.REVENUE, "225522 $")
        }
    }

    private fun createCompleteTrialEvent(): Event {
        val data = JSONObject().apply {
            put("player", "ghost")
        }
        return CompleteTrialEvent(data, System.currentTimeMillis(), "time").apply {
            addPredefinedParameter(PredefinedParameters.REGISTRATION_METHOD, "SMS")
        }
    }

    private fun createCompleteTutorialEvent(): Event {
        val data = JSONObject().apply {
            put("name", "intro")
        }
        return CompleteTutorialEvent(data, System.currentTimeMillis(), "intro").apply {
            addPredefinedParameter(PredefinedParameters.REGISTRATION_METHOD, "SMS")
        }
    }

    private fun createContentItemsViewEvent(): Event {
        val data = listOf(
            JSONObject().apply {
                put("item", "book")
            },
            JSONObject().apply {
                put("item", "guitar")
            }
        )
        return ContentItemsViewEvent(data, "personal").apply {
            addPredefinedParameter(PredefinedParameters.CONTENT, "Greatest Hits")
            addPredefinedParameter(PredefinedParameters.CONTENT_ID, "2561")
            addPredefinedParameter(PredefinedParameters.CONTENT_LIST, "songs, videos")
            addPredefinedParameter(PredefinedParameters.CONTENT_TYPE, "MATURE")
            addPredefinedParameter(PredefinedParameters.CURRENCY, "USD")
        }
    }

    private fun createCustomId01Event(): Event {
        return CustomId01Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId02Event(): Event {
        return CustomId02Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId03Event(): Event {
        return CustomId03Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId04Event(): Event {
        return CustomId04Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId05Event(): Event {
        return CustomId05Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId06Event(): Event {
        return CustomId06Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId07Event(): Event {
        return CustomId07Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId08Event(): Event {
        return CustomId08Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId09Event(): Event {
        return CustomId09Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createCustomId10Event(): Event {
        return CustomId10Event("custom", System.currentTimeMillis(), "custom").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createDeepLinkedEvent(): Event {
        return DeepLinkedEvent(true, "referrer: google").apply {
            addPredefinedParameter(PredefinedParameters.ADREV_AD_TYPE, "interstitial")
            addPredefinedParameter(PredefinedParameters.REGION, "ASIA")
            addPredefinedParameter(PredefinedParameters.CLASS, "student")
        }
    }

    private fun createInitiatePurchaseEvent(): Event {
        val data = JSONObject().apply {
            put("payment", "card")
        }
        return InitiatePurchaseEvent(data, System.currentTimeMillis(), "best price").apply {
            addPredefinedParameter(PredefinedParameters.ORDER_ID, "23123")
            addPredefinedParameter(PredefinedParameters.PRICE, "2.19$")
            addPredefinedParameter(PredefinedParameters.QUANTITY, "1")
        }
    }

    private fun createInitiateStreamEvent(): Event {
        val data = JSONObject().apply {
            put("streamer", "car")
            put("date", "02.03.2021")
        }
        return InitiateStreamEvent(data, System.currentTimeMillis(), "shooter").apply {
            addPredefinedParameter(PredefinedParameters.COUPON_CODE, "25XLKM")
            addPredefinedParameter(PredefinedParameters.VIRTUAL_CURRENCY_NAME, "BTC")
        }
    }

    private fun createInviteEvent(): Event {
        val data = JSONObject().apply {
            put("invitation", "mail")
            put("date", "02.03.2021")
        }
        return InviteEvent(data, System.currentTimeMillis(), "dancing").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createLastAttributedTouchEvent(): Event {
        val touchData = JSONObject().apply {
            put("header", 2)
        }
        return LastAttributedTouchEvent(
            TouchType.CLICK,
            System.currentTimeMillis(),
            touchData,
            "tablet"
        ).apply {
            addPredefinedParameter(PredefinedParameters.SUBSCRIPTION_ID, "lasAK22")
        }
    }

    private fun createListViewEvent(): Event {
        val data = JSONObject().apply {
            put("clothes", 2)
        }
        return ListViewEvent(data, "items").apply {
            addPredefinedParameter(PredefinedParameters.PAYMENT_INFO_AVAILABLE, "card")
            addPredefinedParameter(PredefinedParameters.SEARCH_STRING, "best car wash")
            addPredefinedParameter(PredefinedParameters.SUGGESTED_DESTINATIONS, "crete, spain")
            addPredefinedParameter(
                PredefinedParameters.SUGGESTED_HOTELS,
                "beach resort, marina spa"
            )
        }
    }

    private fun createLoginEvent(): Event {
        val data = JSONObject().apply {
            put("email", "cat@gmail.com")
        }
        return LoginEvent(data, System.currentTimeMillis(), "web").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createOpenedFromPushNotificationEvent(): Event {
        return OpenedFromPushNotificationEvent("silent", "active").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createPurchaseEvent(): Event {
        val data = JSONObject().apply {
            put("phone", 1)
            put("case", 1)
        }
        return PurchaseEvent(data, System.currentTimeMillis(), "apple").apply {
            addPredefinedParameter(PredefinedParameters.ORDER_ID, "23123")
            addPredefinedParameter(PredefinedParameters.PRICE, "2.19$")
            addPredefinedParameter(PredefinedParameters.QUANTITY, "1")
        }
    }

    private fun createRateEvent(): Event {
        val data = JSONObject().apply {
            put("rating", 5)
        }
        return RateEvent(data, System.currentTimeMillis(), "no bugs").apply {
            addPredefinedParameter(PredefinedParameters.PREFERRED_NEIGHBORHOODS, "2")
            addPredefinedParameter(PredefinedParameters.PREFERRED_NUM_STOPS, "4")
            addPredefinedParameter(PredefinedParameters.PREFERRED_PRICE_RANGE, "10-22")
            addPredefinedParameter(PredefinedParameters.PREFERRED_STAR_RATINGS, "4.6")
        }
    }

    private fun createReEngageEvent(): Event {
        return ReEngageEvent("data", "web").apply {
            addPredefinedParameter(PredefinedParameters.CUSTOMER_USER_ID, "4")
        }
    }

    private fun createReserveEvent(): Event {
        val data = JSONObject().apply {
            put("ticket", "movie")
        }
        val data2 = JSONObject().apply {
            put("food", "coke")
        }
        return ReserveEvent(listOf(data, data2), System.currentTimeMillis(), "discount").apply {
            addPredefinedParameter(PredefinedParameters.ORDER_ID, "23123")
            addPredefinedParameter(PredefinedParameters.PRICE, "2.19$")
            addPredefinedParameter(PredefinedParameters.QUANTITY, "1")
        }
    }

    private fun createSalesEvent(): Event {
        val data = JSONObject().apply {
            put("phone", 1)
            put("case", 1)
        }
        return SalesEvent(data, System.currentTimeMillis(), "apple").apply {
            addPredefinedParameter(PredefinedParameters.ORDER_ID, "23123")
            addPredefinedParameter(PredefinedParameters.PRICE, "2.19$")
            addPredefinedParameter(PredefinedParameters.QUANTITY, "1")
        }
    }

    private fun createSearchEvent(): Event {
        val data = JSONArray().apply {
            put("eco milk")
            put("grass")
        }
        return SearchEvent(data, System.currentTimeMillis(), "browser").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createShareEvent(): Event {
        val data = JSONObject().apply {
            put("post_id", "252242")
            put("post_img", "img.webp")
        }

        return ShareEvent(data, System.currentTimeMillis(), "telegram").apply {
            addPredefinedParameter(PredefinedParameters.RECEIPT_ID, "22")
        }
    }

    private fun createSpendCreditsEvent(): Event {
        return SpendCreditsEvent(2142, System.currentTimeMillis(), "boosters").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createStartRegistrationEvent(): Event {
        val data = JSONObject().apply {
            put("time", "19:22:11")
        }
        return StartRegistrationEvent(data, System.currentTimeMillis(), "referrer").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createStartTrialEvent(): Event {
        val data = JSONObject().apply {
            put("time", "19:22:11")
        }
        return StartTrialEvent(data, System.currentTimeMillis(), "30-days").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createStartTutorialEvent(): Event {
        val data = JSONObject().apply {
            put("time", "19:22:11")
            put("reward", "22")
        }

        return StartTutorialEvent(data, System.currentTimeMillis(), "video-feature").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createSubscribeEvent(): Event {
        val data = JSONObject().apply {
            put("streamer", "cat")
        }
        return SubscribeEvent(data, System.currentTimeMillis(), "wire").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createTravelBookingEvent(): Event {
        val data = JSONArray().apply {
            put("may")
            put("august")
        }
        return TravelBookingEvent(data, "booking").apply {
            addPredefinedParameter(PredefinedParameters.NUM_ADULTS, "1")
            addPredefinedParameter(PredefinedParameters.NUM_CHILDREN, "2")
            addPredefinedParameter(PredefinedParameters.NUM_INFANTS, "1")
            addPredefinedParameter(PredefinedParameters.DATE_A, "30.12.2020")
            addPredefinedParameter(PredefinedParameters.DATE_B, "01.01.2021")
            addPredefinedParameter(PredefinedParameters.DEPARTING_ARRIVAL_DATE, "01.01.2021")
            addPredefinedParameter(PredefinedParameters.DEPARTING_DEPARTURE_DATE, "30.12.2020")
            addPredefinedParameter(PredefinedParameters.DESTINATION_A, "usa")
            addPredefinedParameter(PredefinedParameters.DESTINATION_B, "mexico")
            addPredefinedParameter(PredefinedParameters.DESTINATION_LIST, "usa, mexico")
            addPredefinedParameter(PredefinedParameters.HOTEL_SCORE, "5")
            addPredefinedParameter(PredefinedParameters.TRAVEL_START, "01.12.2020")
            addPredefinedParameter(PredefinedParameters.TRAVEL_END, "01.12.2021")
        }
    }

    private fun createUnlockAchievementEvent(): Event {
        val data = JSONObject().apply {
            put("achievement", "new level")
        }
        return UnlockAchievementEvent(data, System.currentTimeMillis(), "best damage").apply {
            addPredefinedParameter(PredefinedParameters.USER_SCORE, "12552")
            addPredefinedParameter(PredefinedParameters.ACHIEVEMENT_ID, "1334-1225-ASDZ")
        }
    }

    private fun createUnsubscribeEvent(): Event {
        val data = JSONObject().apply {
            put("reason", "span")
        }
        return UnsubscribeEvent(data, System.currentTimeMillis(), "02.01.2021").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createUpdateEvent(): Event {
        val data = JSONArray().apply {
            put("com.facebook")
        }
        return UpdateEvent(data, "firmware").apply {
            addPredefinedParameter(PredefinedParameters.EVENT_START, "01.02.2021")
            addPredefinedParameter(PredefinedParameters.EVENT_END, "01.01.2022")
            addPredefinedParameter(PredefinedParameters.NEW_VERSION, "8")
            addPredefinedParameter(PredefinedParameters.OLD_VERSION, "1.8")
        }
    }

    private fun createViewAdvEvent(): Event {
        val data = JSONObject().apply {
            put("source", "amazon")
        }
        return ViewAdvEvent(data, System.currentTimeMillis(), "skip").apply {
            addPredefinedParameter(PredefinedParameters.RETURNING_ARRIVAL_DATE, "01.12.2021")
            addPredefinedParameter(PredefinedParameters.RETURNING_DEPARTURE_DATE, "01.12.2020")
        }
    }

    private fun createViewCartEvent(): Event {
        val data = JSONObject().apply {
            put("cart_value", "25.22$")
            put("cart_items", "2")
        }
        return ViewCartEvent(data, "main").apply {
            addPredefinedParameter(PredefinedParameters.PARAM_01, "param1")
        }
    }

    private fun createViewItemEvent(): Event {
        val data = JSONObject().apply {
            put("section_name", "header")
        }
        return ViewItemEvent(data, "main").apply {
            addPredefinedParameter(PredefinedParameters.MAX_RATING_VALUE, "5")
            addPredefinedParameter(PredefinedParameters.RATING_VALUE, "4.9")
        }
    }

    private fun createViewItemsEvent(): Event {
        val data = JSONArray().apply {
            put(JSONObject().apply { put("section_name", "header") })
            put(JSONObject().apply { put("section_name", "section-1") })
            put(JSONObject().apply { put("section_name", "section-2") })
            put(JSONObject().apply { put("section_name", "footer") })
        }
        return ViewItemsEvent(data, "main").apply {
            addPredefinedParameter(PredefinedParameters.MAX_RATING_VALUE, "5")
            addPredefinedParameter(PredefinedParameters.RATING_VALUE, "4.9")
        }
    }
}