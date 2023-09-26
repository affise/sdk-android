package com.affise.attribution.events

enum class EventName(val eventName: String) {
    ACHIEVE_LEVEL("AchieveLevel"),
    ADD_PAYMENT_INFO("AddPaymentInfo"),
    ADD_TO_CART("AddToCart"),
    ADD_TO_WISHLIST("AddToWishlist"),
    AD_REVENUE("AdRevenue"),
    CLICK_ADV("ClickAdv"),
    COMPLETE_REGISTRATION("CompleteRegistration"),
    COMPLETE_STREAM("CompleteStream"),
    COMPLETE_TRIAL("CompleteTrial"),
    COMPLETE_TUTORIAL("CompleteTutorial"),
    CONTACT("Contact"),
    CONTENT_ITEMS_VIEW("ContentItemsView"),
    CUSTOM_ID_01("CustomId01"),
    CUSTOM_ID_02("CustomId02"),
    CUSTOM_ID_03("CustomId03"),
    CUSTOM_ID_04("CustomId04"),
    CUSTOM_ID_05("CustomId05"),
    CUSTOM_ID_06("CustomId06"),
    CUSTOM_ID_07("CustomId07"),
    CUSTOM_ID_08("CustomId08"),
    CUSTOM_ID_09("CustomId09"),
    CUSTOM_ID_10("CustomId10"),
    CUSTOMIZE_PRODUCT("CustomizeProduct"),
    DEEP_LINKED("DeepLinked"),
    DONATE("Donate"),
    FIND_LOCATION("FindLocation"),
    INITIATE_CHECKOUT("InitiateCheckout"),
    INITIATE_PURCHASE("InitiatePurchase"),
    INITIATE_STREAM("InitiateStream"),
    INVITE("Invite"),
    LAST_ATTRIBUTED_TOUCH("LastAttributedTouch"),
    LEAD("Lead"),
    LIST_VIEW("ListView"),
    LOGIN("Login"),
    OPENED_FROM_PUSH_NOTIFICATION("OpenedFromPushNotification"),
    ORDER("Order"),
    ORDER_ITEM_ADDED("OrderItemAdded"),
    ORDER_ITEM_REMOVE("OrderItemRemove"),
    ORDER_CANCEL("OrderCancel"),
    ORDER_RETURN_REQUEST("OrderReturnRequest"),
    ORDER_RETURN_REQUEST_CANCEL("OrderReturnRequestCancel"),
    PURCHASE("Purchase"),
    RATE("Rate"),
    RE_ENGAGE("ReEngage"),
    RESERVE("Reserve"),
    SALES("Sales"),
    SCHEDULE("Schedule"),
    SEARCH("Search"),
    SHARE("Share"),
    SPEND_CREDITS("SpendCredits"),
    START_REGISTRATION("StartRegistration"),
    START_TRIAL("StartTrial"),
    START_TUTORIAL("StartTutorial"),
    SUBMIT_APPLICATION("SubmitApplication"),
    SUBSCRIBE("Subscribe"),
    TRAVEL_BOOKING("TravelBooking"),
    UNLOCK_ACHIEVEMENT("UnlockAchievement"),
    UNSUBSCRIBE("Unsubscribe"),
    UPDATE("Update"),
    VIEW_ADV("ViewAdv"),
    VIEW_CART("ViewCart"),
    VIEW_CONTENT("ViewContent"),
    VIEW_ITEM("ViewItem"),
    VIEW_ITEMS("ViewItems"),
    ;

    companion object {
        @JvmStatic
        fun from(name: String?): EventName? = name?.let { value ->
            EventName.values().firstOrNull { it.eventName == value }
        }
    }
}