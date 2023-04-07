package com.affise.attribution.parameters.base

import android.app.Application
import android.content.SharedPreferences
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.converter.Converter
import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.CustomLongProvider
import com.affise.attribution.parameters.EmptyStringProvider
import com.affise.attribution.parameters.*
import com.affise.attribution.parameters.factory.PostBackModelFactory
import com.affise.attribution.session.SessionManager
import com.affise.attribution.usecase.FirstAppOpenUseCase
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import com.affise.attribution.utils.SystemAppChecker

/**
 * Factory for [PostBackModelFactory]
 */
internal class PropertiesProviderFactory(
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider,
    private val app: Application,
    private val firstAppOpenUseCase: FirstAppOpenUseCase,
    private val retrieveInstallReferrerUseCase: RetrieveInstallReferrerUseCase,
    private val sessionManager: SessionManager,
    private val sharedPreferences: SharedPreferences,
    private val initPropertiesStorage: InitPropertiesStorage,
    private val stringToMd5Converter: Converter<String, String>,
    private val stringToSha1Converter: Converter<String, String>,
    private val stringToSha256Converter: Converter<String, String>,
    private val logsManager: LogsManager,
    private val deeplinkClickRepository: DeeplinkClickRepository,
    private val installReferrerProvider: InstallReferrerProvider
) {

    fun create(): PostBackModelFactory {
        val androidIdProvider = AndroidIdProvider(app)
        val firstOpenTimeProvider = FirstOpenTimeProvider(firstAppOpenUseCase)
        val lastSessionTimeProvider = LastSessionTimeProvider(sessionManager)

        return PostBackModelFactory(
            providers = listOf(
                UuidProvider(),
                AffiseAppIdProvider(initPropertiesStorage),
                AffisePackageAppNameProvider(app),
                AppVersionProvider(app, logsManager),
                AppVersionRawProvider(app, logsManager),
                StoreProvider(app, logsManager, SystemAppChecker(app)),
                InstalledTimeProvider(app, logsManager),
                firstOpenTimeProvider,
                InstalledHourProvider(app),
                FirstOpenHourProvider(firstAppOpenUseCase),
                InstallFirstEventProvider(firstAppOpenUseCase),
                InstallBeginTimeProvider(retrieveInstallReferrerUseCase),
                InstallFinishTimeProvider(firstAppOpenUseCase),
                ReferrerInstallVersionProvider(retrieveInstallReferrerUseCase),
                ReferralTimeProvider(retrieveInstallReferrerUseCase),
                ReferrerClickTimestampProvider(retrieveInstallReferrerUseCase),
                ReferrerClickTimestampServerProvider(retrieveInstallReferrerUseCase),
                ReferrerGooglePlayInstantProvider(retrieveInstallReferrerUseCase),
                CreatedTimeProvider(),
                CreatedTimeMilliProvider(),
                CreatedTimeHourProvider(),
                CustomLongProvider(Parameters.LAST_TIME_SESSION, 54.0f) {
                    lastSessionTimeProvider.provide()
                        ?.takeIf { it > 0 }
                        ?: firstOpenTimeProvider.provideWithDefault()
                },
                CpuTypeProvider(buildConfigPropertiesProvider),
                HardwareNameProvider(buildConfigPropertiesProvider),
                DeviceManufacturerProvider(buildConfigPropertiesProvider),
                DeeplinkClickPropertyProvider(deeplinkClickRepository),
                EmptyStringProvider(Parameters.DEVICE_ATLAS_ID, 26.0f),
                AffiseDeviceIdProvider(firstAppOpenUseCase),
                AffiseAltDeviceIdProvider(firstAppOpenUseCase),
                androidIdProvider,
                AndroidIdMD5Provider(androidIdProvider, stringToMd5Converter),
                RefTokenProvider(sharedPreferences),
                RefTokensProvider(sharedPreferences),
                installReferrerProvider,
                UserAgentProvider(),
                MCCProvider(app),
                MNCProvider(app),
                RegionProvider(),
                CountryProvider(),
                LanguageProvider(),
                DeviceNameProvider(app),
                DeviceTypeProvider(app),
                OsNameProvider(buildConfigPropertiesProvider),
                PlatformNameProvider(),
                SdkPlatformNameProvider(),
                ApiLevelOSProvider(buildConfigPropertiesProvider),
                AffSDKVersionProvider(),
                OSVersionProvider(buildConfigPropertiesProvider),
                RandomUserIdProvider(firstAppOpenUseCase),
                IsProductionPropertyProvider(initPropertiesStorage),
                TimezoneDeviceProvider(),
                EmptyStringProvider(Parameters.AFFISE_EVENT_TOKEN, 52.0f),
                EmptyStringProvider(Parameters.AFFISE_EVENT_NAME, 53.0f),
                lastSessionTimeProvider,
                TimeSessionProvider(sessionManager),
                AffiseSessionCountProvider(sessionManager),
                LifetimeSessionCountProvider(sessionManager),
                DeeplinkProvider(deeplinkClickRepository),
                AffPartParamNamePropertyProvider(initPropertiesStorage),
                AffPartParamNameTokenPropertyProvider(initPropertiesStorage),
                AffAppTokenPropertyProvider(initPropertiesStorage, stringToSha256Converter),
                EmptyStringProvider(Parameters.LABEL, 62.0f),
//                AffSDKSecretIdProvider(initPropertiesStorage),
                PushTokenProvider(sharedPreferences),
            )
        )
    }
}