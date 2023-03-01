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
        uuidProvider = UuidProvider(),
        affiseAppIdProvider = AffiseAppIdProvider(initPropertiesStorage),
        affisePkgAppNameProvider = AffisePackageAppNameProvider(app),
        appVersionProvider = AppVersionProvider(app, logsManager),
        appVersionRawProvider = AppVersionRawProvider(app, logsManager),
        storeProvider = StoreProvider(app, logsManager, SystemAppChecker(app)),
        installedTimeProvider = InstalledTimeProvider(app, logsManager),
        firstOpenTimeProvider = FirstOpenTimeProvider(firstAppOpenUseCase),
        installedHourProvider = InstalledHourProvider(app),
        firstOpenHourProvider = FirstOpenHourProvider(firstAppOpenUseCase),
        installFirstEventProvider = InstallFirstEventProvider(firstAppOpenUseCase),
        installBeginTimeProvider = InstallBeginTimeProvider(retrieveInstallReferrerUseCase),
        installFinishTimeProvider = InstallFinishTimeProvider(firstAppOpenUseCase),
        referrerInstallVersionProvider = ReferrerInstallVersionProvider(retrieveInstallReferrerUseCase),
        referralTimeProvider = ReferralTimeProvider(retrieveInstallReferrerUseCase),
        referrerClickTimestampProvider = ReferrerClickTimestampProvider(retrieveInstallReferrerUseCase),
        referrerClickTimestampServerProvider = ReferrerClickTimestampServerProvider(retrieveInstallReferrerUseCase),
        referrerGooglePlayInstantProvider = ReferrerGooglePlayInstantProvider(retrieveInstallReferrerUseCase),
        createdTimeProvider = CreatedTimeProvider(),
        createdTimeMilliProvider = CreatedTimeMilliProvider(),
        createdTimeHourProvider = CreatedTimeHourProvider(),
        lastSessionTimeProvider = LastSessionTimeProvider(sessionManager),
        connectionTypeProvider = ConnectionTypeProvider(app),
        cpuTypeProvider = CpuTypeProvider(buildConfigPropertiesProvider),
        hardwareNameProvider = HardwareNameProvider(buildConfigPropertiesProvider),
        networkTypeProvider = NetworkTypeProvider(app),
        deviceManufacturerProvider = DeviceManufacturerProvider(buildConfigPropertiesProvider),
        proxyIpAddressProvider = ProxyIpAddressProvider(app, buildConfigPropertiesProvider),
        deeplinkClickProvider = DeeplinkClickPropertyProvider(deeplinkClickRepository),
        deviceAtlasIdProvider = "", //TODO
        affDeviceIdProvider = AffiseDeviceIdProvider(firstAppOpenUseCase),
        affaltDeviceIdProvider = AffiseAltDeviceIdProvider(firstAppOpenUseCase),
        adidProvider = "", //TODO
        androidIdProvider = androidIdProvider,
        androidIdMd5Provider = AndroidIdMD5Provider(androidIdProvider, stringToMd5Converter),
        macSha1Provider = MacSha1Provider(macProvider, stringToSha1Converter),
        macMd5Provider = MacMD5Provider(macProvider, stringToMd5Converter),
        gaidAdidProvider = GoogleAdvertisingIdProvider(advertisingIdManager),
        gaidAdidMd5Provider = GoogleAdvertisingIdMd5Provider(advertisingIdManager, stringToMd5Converter),
        oaidProvider = OaidProvider(oaidManager),
        oaidMd5Provider = OaidMD5Provider(oaidManager, stringToMd5Converter),
        altstrAdidProvider = "", //TODO
        fireosAdidProvider = "", //TODO
        colorosAdidProvider = "", //TODO
        reftokenProvider = RefTokenProvider(sharedPreferences),
        reftokensProvider = RefTokensProvider(sharedPreferences),
        referrerProvider = installReferrerProvider,
        userAgentProvider = UserAgentProvider(),
        mccodeProvider = MCCProvider(app),
        mncodeProvider = MNCProvider(app),
        ispProvider = IspNameProvider(app),
        regionProvider = RegionProvider(),
        countryProvider = CountryProvider(),
        languageProvider = LanguageProvider(),
        deviceNameProvider = DeviceNameProvider(app),
        deviceTypeProvider = DeviceTypeProvider(app),
        osNameProvider = OsNameProvider(buildConfigPropertiesProvider),
        platformProvider = PlatformNameProvider(),
        sdkPlatformProvider = SdkPlatformNameProvider(),
        apiLevelOsProvider = ApiLevelOSProvider(buildConfigPropertiesProvider),
        affSdkVersionProvider = AffSDKVersionProvider(),
        osVersionProvider = OSVersionProvider(buildConfigPropertiesProvider),
        randomUserIdProvider = RandomUserIdProvider(firstAppOpenUseCase),
        affSdkPosProvider = IsProductionPropertyProvider(initPropertiesStorage),
        timezoneDevProvider = TimezoneDeviceProvider(),
        affEventTokenProvider = "", //TODO
        affEventNameProvider = "", //TODO
        lastTimeSessionProvider = LastSessionTimeProvider(sessionManager),
        timeSessionProvider = TimeSessionProvider(sessionManager),
        affSessionCountProvider = AffiseSessionCountProvider(sessionManager),
        lifetimeSessionCountProvider = LifetimeSessionCountProvider(sessionManager),
        affDeeplinkProvider = DeeplinkProvider(deeplinkClickRepository),
        affpartParamNameProvider = AffPartParamNamePropertyProvider(initPropertiesStorage),
        affpartParamNameTokenProvider = AffPartParamNameTokenPropertyProvider(initPropertiesStorage),
        affAppTokenProvider = AffAppTokenPropertyProvider(initPropertiesStorage, stringToSha256Converter),
        labelProvider = "", //TODO
        affsdkSecretIdProvider = AffSDKSecretIdProvider(initPropertiesStorage),
        pushtokenProvider = PushTokenProvider(sharedPreferences)
    )
}