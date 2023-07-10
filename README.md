# Affise Attribution Android Library

| Artifact | Version |
| -------- | ------- |
| com.affise:attribution  | [![attribution](https://img.shields.io/maven-central/v/com.affise/attribution?label=latest)](https://mvnrepository.com/artifact/com.affise/attribution) |
| com.affise:module-advertising  | [![module-advertising](https://img.shields.io/maven-central/v/com.affise/module-advertising?label=latest)](https://mvnrepository.com/artifact/com.affise/module-advertising) |
| com.affise:module-network  | [![module-network](https://img.shields.io/maven-central/v/com.affise/module-network?label=latest)](https://mvnrepository.com/artifact/com.affise/module-network) |
| com.affise:module-phone  | [![module-phone](https://img.shields.io/maven-central/v/com.affise/module-phone?label=latest)](https://mvnrepository.com/artifact/com.affise/module-phone) |
| com.affise:module-status  | [![module-status](https://img.shields.io/maven-central/v/com.affise/module-status?label=latest)](https://mvnrepository.com/artifact/com.affise/module-status) |

- [Affise Attribution Android Library](#affise-attribution-android-library)
- [Description](#description)
  - [Quick start](#quick-start)
  - [Integration](#integration)
    - [Integrate as dependency](#integrate-as-dependency)
    - [Integrate as file dependency](#integrate-as-file-dependency)
    - [Initialize](#initialize)
    - [Requirements](#requirements)
- [Features](#features)
  - [Device identifiers collection](#device-identifiers-collection)
  - [Events tracking](#events-tracking)
  - [Custom events tracking](#custom-events-tracking)
  - [Predefined event parameters](#predefined-event-parameters)
    - [PredefinedString](#predefinedstring)
    - [PredefinedLong](#predefinedlong)
    - [PredefinedFloat](#predefinedfloat)
    - [PredefinedObject](#predefinedobject)
    - [PredefinedListObject](#predefinedlistobject)
    - [PredefinedListString](#predefinedliststring)
  - [Events buffering](#events-buffering)
  - [Advertising Identifier (google) tracking](#advertising-identifier-google-tracking)
  - [Open Advertising Identifier (huawei) tracking](#open-advertising-identifier-huawei-tracking)
  - [Install referrer tracking](#install-referrer-tracking)
  - [Push token tracking](#push-token-tracking)
  - [Reinstall Uninstall tracking](#reinstall-uninstall-tracking)
  - [APK preinstall tracking](#apk-preinstall-tracking)
  - [Deeplinks](#deeplinks)
  - [Offline mode](#offline-mode)
  - [Disable tracking](#disable-tracking)
  - [Disable background tracking](#disable-background-tracking)
  - [GDPR right to be forgotten](#gdpr-right-to-be-forgotten)
  - [Get referrer](#get-referrer)
  - [Get referrer parameter](#get-referrer-parameter)
    - [Referrer keys](#referrer-keys)
  - [Get module state](#get-module-state)
  - [Get random user Id](#get-random-user-id)
  - [Get random device Id](#get-random-device-id)
  - [WebView tracking](#webview-tracking)
    - [Initialize WebView](#initialize-webview)
    - [Events tracking JS](#events-tracking-js)
    - [Predefined event parameters JS](#predefined-event-parameters-js)
    - [Custom events JS](#custom-events-js)

# Description

Affise SDK is a software you can use to collect app usage statistics, device identifiers, deeplink usage, track install
referrer.

## Quick start

## Integration

### Integrate as dependency

For kotlin build script build.gradle.kts use:

```kotlin
dependencies {
    // Add Affise library 
    implementation("com.affise:attribution:1.6.1")
    // Add Affise modules 
    implementation("com.affise:module-advertising:1.6.1")
    implementation("com.affise:module-network:1.6.1")
    implementation("com.affise:module-phone:1.6.1")
    implementation("com.affise:module-status:1.6.1")
    // Add install referrer
    implementation("com.android.installreferrer:installreferrer:2.2")
}
```

For groovy build script build.gradle use:

```groovy
dependencies {
    // Add Affise library 
    implementation 'com.affise:attribution:1.6.1'
    // Add Affise modules 
    implementation 'com.affise:module-advertising:1.6.1'
    implementation 'com.affise:module-network:1.6.1'
    implementation 'com.affise:module-phone:1.6.1'
    implementation 'com.affise:module-status:1.6.1'
    // Add install referrer
    implementation 'com.android.installreferrer:installreferrer:2.2'
}
```

### Integrate as file dependency

Download latest Affise SDK (`attribution-1.6.1.aar`)
from [releases page](https://github.com/affise/sdk-android/releases) and place this binary to gradle application
module lib directory `app/libs/attribution-1.6.1.aar`

Add library as gradle file dependency to application module build script
Add install referrer library

For kotlin build script build.gradle.kts use:

```kotlin
dependencies {
    // ...
    // Add Affise library 
    implementation(files("libs/attribution-1.6.1.aar"))
    // Add Affise modules 
    implementation(files("libs/module-advertising-1.6.1.aar"))
    implementation(files("libs/module-network-1.6.1.aar"))
    implementation(files("libs/module-phone-1.6.1.aar"))
    implementation(files("libs/module-status-1.6.1.aar"))
    // Add install referrer
    implementation("com.android.installreferrer:installreferrer:2.2")
}
```

For groovy build script build.gradle use:

```groovy
dependencies {
    // ...  
    // Add Affise library 
    implementation files('libs/attribution-1.6.1.aar')
    // Add Affise modules 
    implementation files('libs/module-advertising-1.6.1.aar')
    implementation files('libs/module-network-1.6.1.aar')
    implementation files('libs/module-phone-1.6.1.aar')
    implementation files('libs/module-status-1.6.1.aar')
    // Add install referrer
    implementation 'com.android.installreferrer:installreferrer:2.2'
}
```

### Initialize

After library is added as dependency sync project with gradle files and initialize.

> Demo app [App.kt](app/src/main/java/com/affise/app/application/App.kt)

For kotlin use:

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val properties = AffiseInitProperties(
            "Your appId", //Change to your app id
            "Your SDK secretKey", //Change to your SDK secretKey
        )
        Affise.init(this, properties)
    }
}
```

For java use:

```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AffiseInitProperties properties = new AffiseInitProperties(
            "Your appId", //Change to your app id
            "Your SDK secretKey" //Change to your SDK secretKey
        );
        Affise.init(this, properties);
    }
}
```

### Requirements

For a minimal working functionality your app needs to declare internet permission:

```xml

<manifest>
    <!-- ... -->
    <uses-permission android:name="android.permission.INTERNET"/>
</manifest>
```

# Features

## Device identifiers collection

To match users with events and data library is sending, these identifiers are collected:

- `AFFISE_APP_ID`
- `AFFISE_PKG_APP_NAME`
- `AFFISE_APP_NAME_DASHBOARD`
- `APP_VERSION`
- `APP_VERSION_RAW`
- `STORE`
- `TRACKER_TOKEN`
- `TRACKER_NAME`
- `FIRST_TRACKER_TOKEN`
- `FIRST_TRACKER_NAME`
- `LAST_TRACKER_TOKEN`
- `LAST_TRACKER_NAME`
- `OUTDATED_TRACKER_TOKEN`
- `INSTALLED_TIME`
- `FIRST_OPEN_TIME`
- `INSTALLED_HOUR`
- `FIRST_OPEN_HOUR`
- `INSTALL_BEGIN_TIME`
- `INSTALL_FINISH_TIME`
- `REFERRAL_TIME`
- `CREATED_TIME`
- `CREATED_TIME_MILLI`
- `CREATED_TIME_HOUR`
- `UNINSTALL_TIME`
- `REINSTALL_TIME`
- `LAST_SESSION_TIME`
- `CONNECTION_TYPE`
- `CPU_TYPE`
- `HARDWARE_NAME`
- `NETWORK_TYPE`
- `DEVICE_MANUFACTURER`
- `PROXY_IP_ADDRESS`
- `DEEPLINK_CLICK`
- `DEVICE_ATLAS_ID`
- `AFFISE_DEVICE_ID`
- `AFFISE_ALT_DEVICE_ID`
- `ADID`
- `ANDROID_ID`
- `ANDROID_ID_MD5`
- `MAC_SHA1`
- `MAC_MD5`
- `GAID_ADID`
- `GAID_ADID_MD5`
- `OAID`
- `OAID_MD5`
- `REFTOKEN`
- `REFTOKENS`
- `REFERRER`
- `USER_AGENT`
- `MCCODE`
- `MNCODE`
- `ISP`
- `REGION`
- `COUNTRY`
- `LANGUAGE`
- `DEVICE_NAME`
- `DEVICE_TYPE`
- `OS_NAME`
- `PLATFORM`
- `API_LEVEL_OS`
- `AFFISE_SDK_VERSION`
- `OS_VERSION`
- `RANDOM_USER_ID`
- `AFFISE_SDK_POS`
- `TIMEZONE_DEV`
- `LAST_TIME_SESSION`
- `TIME_SESSION`
- `AFFISE_SESSION_COUNT`
- `LIFETIME_SESSION_COUNT`
- `AFFISE_DEEPLINK`
- `AFFISE_PART_PARAM_NAME`
- `AFFISE_PART_PARAM_NAME_TOKEN`
- `AFFISE_APP_TOKEN`
- `LABEL`
- `AFFISE_SDK_SECRET_ID`
- `UUID`
- `AFFISE_APP_OPENED`
- `PUSHTOKEN`
- `EVENTS`
- `AFFISE_EVENTS_COUNT`

## Events tracking

> Demo app [DefaultEventsFactory.kt](app/src/main/java/com/affise/app/ui/fragments/buttons/factories/DefaultEventsFactory.kt)

For example, we want to track what items usually user adds to shopping cart. To send event first create it with
following code

```kotlin
class Presenter {
    fun onUserAddsItemsToCart(items: String) {
        Affise.sendEvent(AddToCartEvent(userData = items))
    }
}
```

For java use:

```java
class Presenter {
    void onUserAddsItemsToCart(String items) {
        Affise.sendEvent(new AddToCartEvent(items));
    }
}
```

With above example you can implement other events:

- `AchieveLevel`
- `AddPaymentInfo`
- `AddToCart`
- `AddToWishlist`
- `ClickAdv`
- `CompleteRegistration`
- `CompleteStream`
- `CompleteTrial`
- `CompleteTutorial`
- `Contact`
- `ContentItemsView`
- `CustomizeProduct`
- `DeepLinked`
- `Donate`
- `FindLocation`
- `InitiateCheckout`
- `InitiatePurchase`
- `InitiateStream`
- `Invite`
- `LastAttributedTouch`
- `Lead`
- `ListView`
- `Login`
- `OpenedFromPushNotification`
- `Purchase`
- `Rate`
- `ReEngage`
- `Reserve`
- `Sales`
- `Schedule`
- `Search`
- `Share`
- `SpendCredits`
- `StartRegistration`
- `StartTrial`
- `StartTutorial`
- `SubmitApplication`
- `Subscribe`
- `TravelBooking`
- `UnlockAchievement`
- `Unsubscribe`
- `Update`
- `ViewAdv`
- `ViewCart`
- `ViewContent`
- `ViewItem`
- `ViewItems`
- `InitialSubscription`
- `InitialTrial`
- `InitialOffer`
- `ConvertedTrial`
- `ConvertedOffer`
- `TrialInRetry`
- `OfferInRetry`
- `SubscriptionInRetry`
- `RenewedSubscription`
- `FailedSubscriptionFromRetry`
- `FailedOfferFromRetry`
- `FailedTrialFromRetry`
- `FailedSubscription`
- `FailedOfferise`
- `FailedTrial`
- `ReactivatedSubscription`
- `RenewedSubscriptionFromRetry`
- `ConvertedOfferFromRetry`
- `ConvertedTrialFromRetry`
- `Unsubscription`

## Custom events tracking

Use any of custom events if default doesn't fit your scenario:

- `CustomId01`
- `CustomId02`
- `CustomId03`
- `CustomId04`
- `CustomId05`
- `CustomId06`
- `CustomId07`
- `CustomId08`
- `CustomId09`
- `CustomId10`

## Predefined event parameters

To enrich your event with another dimension, you can use predefined parameters for most common cases. 
Add it to any event:

```kotlin
class Presenter {
    fun onUserAddsItemsToCart(items: String) {       
        val event = AddToCartEvent(userData = items).apply {
            addPredefinedParameter(PredefinedString.DESCRIPTION, "best before 2029")
        }
        Affise.sendEvent(event)
    }
}
```

For java use:

```java
class Presenter {
    void onUserAddsItemsToCart(String items) {
        AddToCartEvent event = AddToCartEvent(items);
        event.addPredefinedParameter(PredefinedString.DESCRIPTION, "best before 2029");
        event.addPredefinedParameter(PredefinedFloat.PRICE, 2.19f);
        
        Affise.sendEvent(event);
    }
}
```

In examples above `PredefinedString.DESCRIPTION` and `PredefinedFloat.PRICE` is used, but many others is available:

| PredefinedParameter                           | Type             |
|-----------------------------------------------|------------------|
| [PredefinedString](#predefinedstring)         | String           |
| [PredefinedLong](#predefinedlong)             | Long             |
| [PredefinedFloat](#predefinedfloat)           | Float            |
| [PredefinedObject](#predefinedobject)         | JSONObject       |
| [PredefinedListObject](#predefinedlistobject) | List&lt;JSONObject&gt; |
| [PredefinedListString](#predefinedliststring) | List&lt;String&gt;     |

### PredefinedString

- `ADREV_AD_TYPE`
- `CITY`
- `COUNTRY`
- `REGION`
- `CLASS`
- `CONTENT_ID`
- `CONTENT_TYPE`
- `CURRENCY`
- `CUSTOMER_USER_ID`
- `DESCRIPTION`
- `DESTINATION_A`
- `DESTINATION_B`
- `DESTINATION_LIST`
- `ORDER_ID`
- `PAYMENT_INFO_AVAILABLE`
- `PREFERRED_NEIGHBORHOODS`
- `PURCHASE_CURRENCY`
- `RECEIPT_ID`
- `REGISTRATION_METHOD`
- `SEARCH_STRING`
- `SUBSCRIPTION_ID`
- `SUCCESS`
- `SUGGESTED_DESTINATIONS`
- `SUGGESTED_HOTELS`
- `VALIDATED`
- `ACHIEVEMENT_ID`
- `COUPON_CODE`
- `CUSTOMER_SEGMENT`
- `DEEP_LINK`
- `NEW_VERSION`
- `OLD_VERSION`
- `PARAM_01`
- `PARAM_02`
- `PARAM_03`
- `PARAM_04`
- `PARAM_05`
- `PARAM_06`
- `PARAM_07`
- `PARAM_08`
- `PARAM_09`
- `PARAM_10`
- `REVIEW_TEXT`
- `TUTORIAL_ID`
- `VIRTUAL_CURRENCY_NAME`
- `STATUS`

### PredefinedLong

- `DATE_A`
- `DATE_B`
- `DEPARTING_ARRIVAL_DATE`
- `DEPARTING_DEPARTURE_DATE`
- `HOTEL_SCORE`
- `LEVEL`
- `MAX_RATING_VALUE`
- `NUM_ADULTS`
- `NUM_CHILDREN`
- `NUM_INFANTS`
- `PREFERRED_NUM_STOPS`
- `PREFERRED_STAR_RATINGS`
- `QUANTITY`
- `RATING_VALUE`
- `RETURNING_ARRIVAL_DATE`
- `RETURNING_DEPARTURE_DATE`
- `SCORE`
- `TRAVEL_START`
- `TRAVEL_END`
- `USER_SCORE`
- `EVENT_START`
- `EVENT_END`
- `NUM_ITEMS`

### PredefinedFloat

- `PREFERRED_PRICE_RANGE`
- `PRICE`
- `REVENUE`
- `LAT`
- `LONG`

### PredefinedObject

- `CONTENT`

### PredefinedListObject

- `CONTENT_LIST`

### PredefinedListString

- `CONTENT_IDS`

## Events buffering

Affise library will send any pending events with first opportunity,
but if there is no network connection or device is disabled, events are kept locally for 7 days before deletion.

## Advertising Identifier (google) tracking

Advertising Identifier (google) tracking is supported automatically, no actions needed

## Open Advertising Identifier (huawei) tracking

Open Advertising Identifier is supported automatically, no actions needed

## Install referrer tracking

Install referrer tracking is supported automatically, no actions needed

## Push token tracking

To let affise track push token you need to receive it from your push service provider, and pass to Affise library.
First add firebase integration to your app completing these steps: [Firebase Docs](https://firebase.google.com/docs/cloud-messaging/android/client)

After you have done with firebase integration, add to your cloud messaging service `onNewToken` method `Affise.addPushToken(token)`

```kotlin
class FirebaseCloudMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        // New token generated
        Affise.addPushToken(token)
    }
}
```

## Reinstall Uninstall tracking

Affise automaticly track reinstall events by using silent-push technology, to make this feature work, pass push token when it is recreated by user and on you application starts up

```kotlin
Affise.addPushToken(token)
```

## APK preinstall tracking

SDK is also supports scenario when APK is installed not from one of application markets, such as google play, huawei appgallery or amazon appstore
To use this feature, create file with name `partner_key` in your app assets directory, and write unique identifier inside, this key will be passed to our backend so you can track events by partner later in your Affise console.

## Deeplinks

To integrate applink support you need:

- add intent filter to one of your activities, replacing YOUR_AFFISE_APP_ID with id from your affise personal cabinet

```xml
<intent-filter android:autoVerify="true">
  <action android:name="android.intent.action.VIEW" />
  <category android:name="android.intent.category.DEFAULT" />
  <category android:name="android.intent.category.BROWSABLE" />
  <data android:scheme="https" />
  <data android:host="YOUR_AFFISE_APP_ID.affattr.com" />
</intent-filter>
```

- register applink callback right after Affise.init(..)

for kotlin:

```kotlin
Affise.init(..)
Affise.registerDeeplinkCallback { uri ->
  val screen = uri.getQueryParameter("screen")
  if(screen == "special_offer") {
    // open special offer activity
  } else {
    // open another activity
  }
  // return true if deeplink is handled successfully
  true
}
```

for java:

```java
Affise.registerDeeplinkCallback(uri -> {
  String screen = uri.getQueryParameter("screen");
  if (screen.equals("special_offer")) {
    // open special offer screen
  } else {
    // open another activity
  }
  // return true if deeplink is handled successfully
  return true;
});
```

## Offline mode

In some scenarios you would want to limit Affise network usage, to pause that activity call anywhere in your application following code after Affise init:

```kotlin
Affise.init(..)
Affise.setOfflineModeEnabled(enabled = true) // to enable offline mode
Affise.setOfflineModeEnabled(enabled = false) // to disable offline mode
```

While offline mode is enabled, your metrics and other events are kept locally, and will be delivered once offline mode is disabled.
Offline mode is persistent as Application lifecycle, and will be disabled with process termination automatically.
To check current offline mode status call:

```kotlin
Affise.isOfflineModeEnabled() // returns true or false describing current tracking state
```

## Disable tracking

To disable any tracking activity, storing events and gathering device identifiers and metrics call anywhere in your application following code after Affise init:

```kotlin
Affise.init(..)
Affise.setTrackingEnabled(enabled = true) // to enable tracking
Affise.setTrackingEnabled(enabled = false) // to disable tracking
```

By default tracking is enabled.

While tracking mode is disabled, metrics and other identifiers is not generated locally.
Keep in mind that this flag is persistent until app reinstall, and don't forget to reactivate tracking when needed.
To check current status of tracking call:

```kotlin
Affise.isTrackingEnabled() // returns true or false describing current tracking state
```

## Disable background tracking

To disable any background tracking activity, storing events and gathering device identifiers and metrics call anywhere in your application following code after Affise init:

```kotlin
Affise.init(..)
Affise.setBackgroundTrackingEnabled(enabled = true) // to enable background tracking
Affise.setBackgroundTrackingEnabled(enabled = false) // to disable background tracking
```

By default background tracking is enabled.

While background tracking mode is disabled, metrics and other identifiers is not generated locally.
Background tracking mode is persistent as Application lifecycle, and will be re-enabled with process termination automatically.
To check current status of background tracking call:

```kotlin
Affise.isBackgroundTrackingEnabled() // returns true or false describing current background tracking state
```

## GDPR right to be forgotten

Under the EU's General Data Protection Regulation (GDPR): An individual has the right to have their personal data erased.
To provide this functionality to user, as the app developer, you can call 

```kotlin
Affise.init(..)
Affise.forget() // to forget users data
```

After processing such request our backend servers will delete all users data. 
To prevent library from generating new events, disable tracking just before calling Affise.forget: 

```kotlin
Affise.init(..)
Affise.setTrackingEnabled(enabled = false)
Affise.forget() // to forget users data
```

## Get referrer

Use the next public method of SDK

For kotlin:

```kotlin
Affise.getReferrer { referrer ->
  // handle referrer
}
```

For java:

```java
Affise.getReferrer(referrer -> {
    // handle referrer
});
```

## Get referrer parameter

Use the next public method of SDK to get referrer parameter by

For kotlin:

```kotlin
Affise.getReferrerValue(ReferrerKey.CLICK_ID) { value ->
  // handle referrer value
}
```

For java:

```java
Affise.getReferrerValue(ReferrerKey.CLICK_ID, value -> {
    // handle referrer value
});
```

### Referrer keys

In examples above `ReferrerKey.CLICK_ID` is used, but many others is available:

- `AD_ID`
- `CAMPAIGN_ID`
- `CLICK_ID`
- `AFFISE_AD`
- `AFFISE_AD_ID`
- `AFFISE_AD_TYPE`
- `AFFISE_ADSET`
- `AFFISE_ADSET_ID`
- `AFFISE_AFFC_ID`
- `AFFISE_CHANNEL`
- `AFFISE_CLICK_LOOK_BACK`
- `AFFISE_COST_CURRENCY`
- `AFFISE_COST_MODEL`
- `AFFISE_COST_VALUE`
- `AFFISE_DEEPLINK`
- `AFFISE_KEYWORDS`
- `AFFISE_MEDIA_TYPE`
- `AFFISE_MODEL`
- `AFFISE_OS`
- `AFFISE_PARTNER`
- `AFFISE_REF`
- `AFFISE_SITE_ID`
- `AFFISE_SUB_SITE_ID`
- `AFFC`
- `PID`
- `SUB_1`
- `SUB_2`
- `SUB_3`
- `SUB_4`
- `SUB_5`

## Get module state

For kotlin:

```kotlin
Affise.getStatus(AffiseModules.Status) { response ->
    // handle response
}
```

For java:

```java
Affise.getStatus(AffiseModules.Status, response -> {
    // handle response
});
```

## Get random user Id

Use the next public method of SDK

For kotlin:

```kotlin
Affise.getRandomUserId()
```

For java:

```java
Affise.getRandomUserId();
```

## Get random device Id

Use the next public method of SDK

For kotlin:

```kotlin
Affise.getRandomDeviceId()
```

For java:

```java
Affise.getRandomDeviceId();
```

## WebView tracking

### Initialize WebView

To integrate the library into the JavaScript environment, we added a bridge between JavaScript and the native SDK. Now you can send events and use the functionality of the native library directly from WebView.
Here are step by step instructions:

```kotlin
// retrieve WebView from view hierarchy
val webView = findViewById<WebView>(R.Id.your_webview_id)
// make sure javascript is enabled
webView.javaScriptEnabled = true
// initialize WebView with Affise native library
Affise.registerWebView(webView)
```

Other Javascript environment features is described below.

### Events tracking JS

> Demo app [index.html](app/src/main/assets/index.html)

After WebView is initialized you send events from JavaScript environment 

```javascript
let event = new AddPaymentInfoEvent({
  userData: 'taxi',
});

event.addPredefinedParameter(PredefinedString.PURCHASE_CURRENCY, 'USD');
event.addPredefinedParameter(PredefinedFloat.PRICE, 2.19);

Affise.sendEvent(event)
```

Just like with native SDK, javascript environment also provides default events that can be passed from WebView:

- `AchieveLevelEvent`
- `AddPaymentInfoEvent`
- `AddToCartEvent`
- `AddToWishlistEvent`
- `ClickAdvEvent`
- `CompleteRegistrationEvent`
- `CompleteStreamEvent`
- `CompleteTrialEvent`
- `CompleteTutorialEvent`
- `ContactEvent`
- `ContentItemsViewEvent`
- `CustomId01Event`
- `CustomId02Event`
- `CustomId03Event`
- `CustomId04Event`
- `CustomId05Event`
- `CustomId06Event`
- `CustomId07Event`
- `CustomId08Event`
- `CustomId09Event`
- `CustomId10Event`
- `CustomizeProductEvent`
- `DeepLinkedEvent`
- `DonateEvent`
- `FindLocationEvent`
- `InitiateCheckoutEvent`
- `InitiatePurchaseEvent`
- `InitiateStreamEvent`
- `InviteEvent`
- `LastAttributedTouchEvent`
- `LeadEvent`
- `ListViewEvent`
- `LoginEvent`
- `OpenedFromPushNotificationEvent`
- `PurchaseEvent`
- `RateEvent`
- `ReEngageEvent`
- `ReserveEvent`
- `SalesEvent`
- `ScheduleEvent`
- `SearchEvent`
- `ShareEvent`
- `SpendCreditsEvent`
- `StartRegistrationEvent`
- `StartTrialEvent`
- `StartTutorialEvent`
- `SubmitApplicationEvent`
- `SubscribeEvent`
- `TravelBookingEvent`
- `UnlockAchievementEvent`
- `UnsubscribeEvent`
- `UpdateEvent`
- `ViewAdvEvent`
- `ViewCartEvent`
- `ViewContentEvent`
- `ViewItemEvent`
- `ViewItemsEvent`
- `InitialSubscriptionEvent`
- `InitialTrialEvent`
- `InitialOfferEvent`
- `ConvertedTrialEvent`
- `ConvertedOfferEvent`
- `TrialInRetryEvent`
- `OfferInRetryEvent`
- `SubscriptionInRetryEvent`
- `RenewedSubscriptionEvent`
- `FailedSubscriptionFromRetryEvent`
- `FailedOfferFromRetryEvent`
- `FailedTrialFromRetryEvent`
- `FailedSubscriptionEvent`
- `FailedOfferiseEvent`
- `FailedTrialEvent`
- `ReactivatedSubscriptionEvent`
- `RenewedSubscriptionFromRetryEvent`
- `ConvertedOfferFromRetryEvent`
- `ConvertedTrialFromRetryEvent`
- `UnsubscriptionEvent`

### Predefined event parameters JS

Each event can be extended with custom event parameters. By calling `addPredefinedParameter` function you can pass predefined parameters name and value, for example:

```javascript
let event = ...

event.addPredefinedParameter(PredefinedString.PURCHASE_CURRENCY, 'USD');
event.addPredefinedParameter(PredefinedFloat.PRICE, 2.19);

Affise.sendEvent(event);
```

### Custom events JS

If above event functionality still limits your use case, you can always extend `Event` class to override fields you are missing

```javascript
class MyCustomEvent extends Event {
    constructor(args) {
        super('MyCustom', args)
    }
}
```
