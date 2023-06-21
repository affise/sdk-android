# Affise Attribution Android Library

| Artifact | Version |
| -------- | ------- |
| com.affise:attribution  | <a href="https://mvnrepository.com/artifact/com.affise/attribution"><img src="https://img.shields.io/maven-central/v/com.affise/attribution?label=latest"  alt="version" /></a> |
| com.affise:module-advertising  | <a href="https://mvnrepository.com/artifact/com.affise/module-advertising"><img src="https://img.shields.io/maven-central/v/com.affise/module-advertising?label=latest"  alt="version" /></a> |
| com.affise:module-network  | <a href="https://mvnrepository.com/artifact/com.affise/module-network"><img src="https://img.shields.io/maven-central/v/com.affise/module-network?label=latest" alt="version" /></a> |
| com.affise:module-phone  | <a href="https://mvnrepository.com/artifact/com.affise/module-phone"><img src="https://img.shields.io/maven-central/v/com.affise/module-phone?label=latest" alt="version" /></a> |
| com.affise:module-status  | <a href="https://mvnrepository.com/artifact/com.affise/module-status"><img src="https://img.shields.io/maven-central/v/com.affise/module-status?label=latest" alt="version" /></a> |

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
  - [Webview tracking](#webview-tracking)
    - [Initialize webview](#initialize-webview)
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
    implementation("com.affise:attribution:1.5.6")
    // Add Affise modules 
    implementation("com.affise:module-advertising:1.5.6")
    implementation("com.affise:module-network:1.5.6")
    implementation("com.affise:module-phone:1.5.6")
    implementation("com.affise:module-status:1.5.6")
    // Add install referrer
    implementation("com.android.installreferrer:installreferrer:2.2")
}
```

For groovy build script build.gradle use:

```groovy
dependencies {
    // Add Affise library 
    implementation 'com.affise:attribution:1.5.6'
    // Add Affise modules 
    implementation 'com.affise:module-advertising:1.5.6'
    implementation 'com.affise:module-network:1.5.6'
    implementation 'com.affise:module-phone:1.5.6'
    implementation 'com.affise:module-status:1.5.6'
    // Add install referrer
    implementation 'com.android.installreferrer:installreferrer:2.2'
}
```

### Integrate as file dependency

Download latest Affise SDK (`attribution-1.5.6.aar`)
from [releases page](https://github.com/affise/sdk-android/releases) and place this binary to gradle application
module lib directory `app/libs/attribution-1.5.6.aar`

Add library as gradle file dependency to application module build script
Add install referrer library

For kotlin build script build.gradle.kts use:

```kotlin
dependencies {
    // ...
    // Add Affise library 
    implementation(files("libs/attribution-1.5.6.aar"))
    // Add Affise modules 
    implementation(files("libs/module-advertising-1.5.6.aar"))
    implementation(files("libs/module-network-1.5.6.aar"))
    implementation(files("libs/module-phone-1.5.6.aar"))
    implementation(files("libs/module-status-1.5.6.aar"))
    // Add install referrer
    implementation("com.android.installreferrer:installreferrer:2.2")
}
```

For groovy build script build.gradle use:

```groovy
dependencies {
    // ...  
    // Add Affise library 
    implementation files('libs/attribution-1.5.6.aar')
    // Add Affise modules 
    implementation files('libs/module-advertising-1.5.6.aar')
    implementation files('libs/module-network-1.5.6.aar')
    implementation files('libs/module-phone-1.5.6.aar')
    implementation files('libs/module-status-1.5.6.aar')
    // Add install referrer
    implementation 'com.android.installreferrer:installreferrer:2.2'
}
```

### Initialize

After library is added as dependency sync project with gradle files and initialize.

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

For example, we want to track what items usually user adds to shopping cart. To send event first create it with
following code

```kotlin
class Presenter {
    fun onUserAddsItemsToCart(items: String) {
        val items = JSONObject().apply {
            put("items", "cookies, potato, milk")
        }
        Affise.sendEvent(AddToCartEvent(items, System.currentTimeMillis(), "groceries"))
    }
}
```

For java use:

```java
class Presenter {
    void onUserAddsItemsToCart(String items) {
        JSONObject items = new JSONObject();
        items.put("items", items);
        Affise.sendEvent(new AddToCartEvent(items, System.currentTimeMillis(), "groceries"));
    }
}
```

With above example you can implement other events:

- `AchieveLevelEvent`
- `AddPaymentInfoEvent`
- `AddToCartEvent`
- `AddToWishlistEvent`
- `ClickAdvEvent`
- `CompleteRegistrationEvent`
- `CompleteStreamEvent`
- `CompleteTrialEvent`
- `CompleteTutorialEvent`
- `ContentItemsViewEvent`
- `DeepLinkedEvent`
- `InitiatePurchaseEvent`
- `InitiateStreamEvent`
- `InviteEvent`
- `LastAttributedTouchEvent`
- `ListViewEvent`
- `LoginEvent`
- `OpenedFromPushNotificationEvent`
- `PurchaseEvent`
- `RateEvent`
- `ReEngageEvent`
- `ReserveEvent`
- `SalesEvent`
- `SearchEvent`
- `ShareEvent`
- `SpendCreditsEvent`
- `StartRegistrationEvent`
- `StartTrialEvent`
- `StartTutorialEvent`
- `SubscribeEvent`
- `TravelBookingEvent`
- `UnlockAchievementEvent`
- `UnsubscribeEvent`
- `UnsubscriptionEvent`
- `UpdateEvent`
- `ViewAdvEvent`
- `ViewCartEvent`
- `ViewItemEvent`
- `ViewItemsEvent`

## Custom events tracking

Use any of custom events if default doesn't fit your scenario:

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

## Predefined event parameters

To enrich your event with another dimension, you can use predefined parameters for most common cases. 
Add it to any event:

```kotlin
class Presenter {
    fun onUserAddsItemsToCart(items: String) {
        val items = JSONObject().apply {
            put("items", "cookies, potato, milk")
        }
        
        val event = AddToCartEvent(items, System.currentTimeMillis()).apply {
            addPredefinedParameter(PredefinedParameters.DESCRIPTION, "best before 2029")
        }
        Affise.sendEvent(event)
    }
}
```

For java use:

```java
class Presenter {
    void onUserAddsItemsToCart(String items) {
        JSONObject items = new JSONObject();
        items.put("items", items);
        
        AddToCartEvent event = AddToCartEvent(items, System.currentTimeMillis());
        event.addPredefinedParameter(PredefinedParameters.DESCRIPTION, "best before 2029");
        
        Affise.sendEvent(event);
    }
}
```

In examples above `PredefinedParameters.DESCRIPTION` is used, but many others is available:

- `ADREV_AD_TYPE`
- `CITY`
- `COUNTRY`
- `REGION`
- `CLASS`
- `CONTENT`
- `CONTENT_ID`
- `CONTENT_LIST`
- `CONTENT_TYPE`
- `CURRENCY`
- `CUSTOMER_USER_ID`
- `DATE_A`
- `DATE_B`
- `DEPARTING_ARRIVAL_DATE`
- `DEPARTING_DEPARTURE_DATE`
- `DESCRIPTION`
- `DESTINATION_A`
- `DESTINATION_B`
- `DESTINATION_LIST`
- `HOTEL_SCORE`
- `LEVEL`
- `MAX_RATING_VALUE`
- `NUM_ADULTS`
- `NUM_CHILDREN`
- `NUM_INFANTS`
- `ORDER_ID`
- `PAYMENT_INFO_AVAILABLE`
- `PREFERRED_NEIGHBORHOODS`
- `PREFERRED_NUM_STOPS`
- `PREFERRED_PRICE_RANGE`
- `PREFERRED_STAR_RATINGS`
- `PRICE`
- `PURCHASE_CURRENCY`
- `QUANTITY`
- `RATING_VALUE`
- `RECEIPT_ID`
- `REGISTRATION_METHOD`
- `RETURNING_ARRIVAL_DATE`
- `RETURNING_DEPARTURE_DATE`
- `REVENUE`
- `SCORE`
- `SEARCH_STRING`
- `SUBSCRIPTION_ID`
- `SUCCESS`
- `SUGGESTED_DESTINATIONS`
- `SUGGESTED_HOTELS`
- `TRAVEL_START`
- `TRAVEL_END`
- `USER_SCORE`
- `VALIDATED`
- `ACHIEVEMENT_ID`
- `COUPON_CODE`
- `CUSTOMER_SEGMENT`
- `DEEP_LINK`
- `EVENT_START`
- `EVENT_END`
- `LAT`
- `LONG`
- `NEW_VERSION`
- `OLD_VERSION`
- `REVIEW_TEXT`
- `TUTORIAL_ID`
- `VIRTUAL_CURRENCY_NAME`
- `PARAM_01`
- `PARAM_02`
- `PARAM_03`
- `PARAM_04`
- `PARAM_05`
- `PARAM_06`
- `PARAM_07`
- `PARAM_08`
- `PARAM_09`

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
First add firebase integration to your app completing theese steps: [Firebase Docs](https://firebase.google.com/docs/cloud-messaging/android/client)

After you have done with firebase inegration, add to your cloud messaging service `onNewToken` method `Affise.addPushToken(token)`

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

In some scenarious you would want to limit Affise network usage, to pause that activity call anywhere in your application following code after Affise init:

```kotlin
Affise.init(..)
Affise.setOfflineModeEnabled(enabled = true) // to enable offline mode
Affise.setOfflineModeEnabled(enabled = false) // to disable offline mode
```

While offline mode is enabled, your metrics and other events are kept locally, and will be delivered once offline mode is disabled.
Offline mode is persistent as Application lifecycle, and will be disabled with process termination automaticly.
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

```kotlin
Affise.getReferrer()
```

## Get referrer parameter

Use the next public method of SDK to get referrer parameter by

For kotlin:

```kotlin
Affise.getReferrerValue(ReferrerKey.CLICK_ID) { value ->

}
```

For java:

```java
Affise.getReferrerValue(ReferrerKey.CLICK_ID, value -> {

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

## Webview tracking

### Initialize webview

To integrate the library into the JavaScript environment, we added a bridge between JavaScript and the native SDK. Now you can send events and use the functionality of the native library directly from Webview.
Here are step by step instructions:

```kotlin
// retreive webview from view hierarhy
val webView = findViewById<WebView>(R.Id.your_webview_id)
// make sure javascript is enabled
webView.javaScriptEnabled = true
// initialize webview with Affise native library
Affise.registerWebView(webView)
```

Other Javascript enviroment features is described below.

### Events tracking JS

after webview is initialized you send events from JavaScript enviroment 

```javascript
var event = new AddPaymentInfoEvent(
    { card: 4138, type: 'phone' },
     Date.now(),
    'taxi'
);

event.addPredefinedParameter('affise_p_purchase_currency', 'USD');

Affise.sendEvent(event);
```

Just like with native SDK, javascript enviroment also provides default events that can be passed from webview:

- `AchieveLevelEvent`
- `AddPaymentInfoEvent`
- `AddToCartEvent`
- `AddToWishlistEvent`
- `ClickAdvEvent`
- `CompleteRegistrationEvent`
- `CompleteStreamEvent`
- `CompleteTrialEvent`
- `CompleteTutorialEvent`
- `ContentItemsViewEvent`
- `ConvertedOfferEvent`
- `ConvertedOfferFromRetryEvent`
- `ConvertedTrialEvent`
- `ConvertedTrialFromRetryEvent`
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
- `DeepLinkedEvent`
- `FailedOfferFromRetryEvent`
- `FailedOfferiseEvent`
- `FailedSubscriptionEvent`
- `FailedSubscriptionFromRetryEvent`
- `FailedTrialEvent`
- `FailedTrialFromRetryEvent`
- `InitialOfferEvent`
- `InitialSubscriptionEvent`
- `InitialTrialEvent`
- `InitiatePurchaseEvent`
- `InitiateStreamEvent`
- `InviteEvent`
- `LastAttributedTouchEvent`
- `ListViewEvent`
- `LoginEvent`
- `OfferInRetryEvent`
- `OpenedFromPushNotificationEvent`
- `PurchaseEvent`
- `RateEvent`
- `ReEngageEvent`
- `ReactivatedSubscriptionEvent`
- `RenewedSubscriptionEvent`
- `RenewedSubscriptionFromRetryEvent`
- `ReserveEvent`
- `SalesEvent`
- `SearchEvent`
- `ShareEvent`
- `SpendCreditsEvent`
- `StartRegistrationEvent`
- `StartTrialEvent`
- `StartTutorialEvent`
- `SubscribeEvent`
- `SubscriptionEvent`
- `SubscriptionInRetryEvent`
- `TravelBookingEvent`
- `TrialInRetryEvent`
- `UnlockAchievementEvent`
- `UnsubscribeEvent`
- `UnsubscriptionEvent`
- `UpdateEvent`
- `ViewAdvEvent`
- `ViewCartEvent`
- `ViewItemEvent`
- `ViewItemsEvent`

### Predefined event parameters JS

Each event can be extended with custom event parameters. By calling `addPredefinedParameter` function you can pass predefined parameters name and value, for example:

```javascript
var event = ...

event.addPredefinedParameter('affise_p_purchase_currency', 'USD');

Affise.sendEvent(event);
```

### Custom events JS

If above event functionality still limits your usecase, you can allways extend `Event` class to override fields you are missing

```javascript
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
```
