package com.affise.attribution

import android.content.SharedPreferences
import com.affise.attribution.deeplink.DeeplinkManagerImpl
import com.affise.attribution.events.EventsManager
import com.affise.attribution.events.StoreEventUseCase
import com.affise.attribution.events.autoCatchingClick.AutoCatchingClickProvider
import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.init.SetPropertiesWhenAppInitializedUseCase
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.metrics.MetricsManager
import com.affise.attribution.parameters.InstallReferrerProvider
import com.affise.attribution.session.SessionManager
import com.affise.attribution.test.CrashApplicationUseCase
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import com.affise.attribution.usecase.EraseUserDataUseCaseImpl
import com.affise.attribution.usecase.FirstAppOpenUseCase
import com.affise.attribution.usecase.PreferencesUseCaseImpl
import com.affise.attribution.usecase.SendGDPREventUseCaseImpl
import com.affise.attribution.webBridge.WebBridgeManager

/**
 * Library api contract
 */
internal interface AffiseApi {
    val setPropertiesWhenInitUseCase: SetPropertiesWhenAppInitializedUseCase
    val firstAppOpenUseCase: FirstAppOpenUseCase
    val sessionManager: SessionManager
    val eventsManager: EventsManager
    val storeEventUseCase: StoreEventUseCase
    val sharedPreferences: SharedPreferences
    val logsManager: LogsManager
    val webBridgeManager: WebBridgeManager
    val deeplinkManager: DeeplinkManagerImpl
    val initPropertiesStorage: InitPropertiesStorage
    val autoCatchingClickProvider: AutoCatchingClickProvider
    val preferencesUseCase: PreferencesUseCaseImpl
    val eraseUserDataUseCase: EraseUserDataUseCaseImpl
    val sendGDPREventUseCase: SendGDPREventUseCaseImpl
    val metricsManager: MetricsManager
    val crashApplicationUseCase: CrashApplicationUseCase
    val installReferrerProvider: InstallReferrerProvider
    val retrieveInstallReferrerUseCase: RetrieveInstallReferrerUseCase
}