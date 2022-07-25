package com.affise.attribution.parameters.base

import android.app.Application
import android.content.SharedPreferences
import com.affise.attribution.advertising.AdvertisingIdManager
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.converter.Converter
import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.oaid.OaidManager
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
    private val advertisingIdManager: AdvertisingIdManager,
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider,
    private val app: Application,
    private val firstAppOpenUseCase: FirstAppOpenUseCase,
    private val oaidManager: OaidManager,
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

    private val macProvider = MacProvider(logsManager)
    private val androidIdProvider = AndroidIdProvider(app)

    fun create(): PostBackModelFactory = PostBackModelFactory(
        UuidProvider(),
        AffiseAppIdProvider(initPropertiesStorage),
        AffisePackageAppNameProvider(app),
        AppVersionProvider(app, logsManager),
        AppVersionRawProvider(app, logsManager),
        StoreProvider(app, logsManager, SystemAppChecker(app)),
        InstalledTimeProvider(app, logsManager),
        FirstOpenTimeProvider(firstAppOpenUseCase),
        InstalledHourProvider(app),
        FirstOpenHourProvider(firstAppOpenUseCase),
        InstallBeginTimeProvider(retrieveInstallReferrerUseCase),
        InstallFinishTimeProvider(firstAppOpenUseCase),
        ReferralTimeProvider(retrieveInstallReferrerUseCase),
        CreatedTimeProvider(),
        CreatedTimeMilliProvider(),
        CreatedTimeHourProvider(),
        LastSessionTimeProvider(sessionManager),
        ConnectionTypeProvider(app),
        CpuTypeProvider(buildConfigPropertiesProvider),
        HardwareNameProvider(buildConfigPropertiesProvider),
        NetworkTypeProvider(app),
        DeviceManufacturerProvider(buildConfigPropertiesProvider),
        ProxyIpAddressProvider(app, buildConfigPropertiesProvider),
        DeeplinkClickPropertyProvider(deeplinkClickRepository),
        "", //TODO
        AffiseDeviceIdProvider(firstAppOpenUseCase),
        AffiseAltDeviceIdProvider(firstAppOpenUseCase),
        "", //TODO
        androidIdProvider,
        AndroidIdMD5Provider(androidIdProvider, stringToMd5Converter),
        MacSha1Provider(macProvider, stringToSha1Converter),
        MacMD5Provider(macProvider, stringToMd5Converter),
        GoogleAdvertisingIdProvider(advertisingIdManager),
        GoogleAdvertisingIdMd5Provider(advertisingIdManager, stringToMd5Converter),
        OaidProvider(oaidManager),
        OaidMD5Provider(oaidManager, stringToMd5Converter),
        "", //TODO
        "", //TODO
        "", //TODO
        RefTokenProvider(sharedPreferences),
        RefTokensProvider(sharedPreferences),
        installReferrerProvider,
        UserAgentProvider(),
        MCCProvider(app),
        MNCProvider(app),
        IspNameProvider(app),
        RegionProvider(),
        CountryProvider(),
        LanguageProvider(),
        DeviceNameProvider(app),
        DeviceTypeProvider(app),
        OsNameProvider(buildConfigPropertiesProvider),
        PlatformNameProvider(),
        ApiLevelOSProvider(buildConfigPropertiesProvider),
        AffSDKVersionProvider(),
        OSVersionProvider(buildConfigPropertiesProvider),
        RandomUserIdProvider(firstAppOpenUseCase),
        IsProductionPropertyProvider(initPropertiesStorage),
        TimezoneDeviceProvider(),
        "", //TODO
        "", //TODO
        LastSessionTimeProvider(sessionManager),
        TimeSessionProvider(sessionManager),
        AffiseSessionCountProvider(sessionManager),
        LifetimeSessionCountProvider(sessionManager),
        DeeplinkProvider(deeplinkClickRepository),
        AffPartParamNamePropertyProvider(initPropertiesStorage),
        AffPartParamNameTokenPropertyProvider(initPropertiesStorage),
        AffAppTokenPropertyProvider(initPropertiesStorage, stringToSha256Converter),
        "", //TODO
        AffSDKSecretIdProvider(initPropertiesStorage),
        PushTokenProvider(sharedPreferences)
    )
}