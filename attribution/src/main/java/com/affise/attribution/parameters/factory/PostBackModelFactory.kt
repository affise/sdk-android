package com.affise.attribution.parameters.factory

import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.SerializedLog
import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.*
import com.affise.attribution.parameters.AffSDKSecretIdProvider
import com.affise.attribution.parameters.AffiseAltDeviceIdProvider
import com.affise.attribution.parameters.AffiseDeviceIdProvider
import com.affise.attribution.parameters.AffiseSessionCountProvider
import com.affise.attribution.parameters.FirstOpenHourProvider
import com.affise.attribution.parameters.FirstOpenTimeProvider
import com.affise.attribution.parameters.InstallFinishTimeProvider
import com.affise.attribution.parameters.RandomUserIdProvider

internal class PostBackModelFactory(
    val uuidProvider: UuidProvider,
    val affiseAppIdProvider: AffiseAppIdProvider,
    val affisePkgAppNameProvider: AffisePackageAppNameProvider,
    val appVersionProvider: AppVersionProvider,
    val appVersionRawProvider: AppVersionRawProvider,
    val storeProvider: StoreProvider,
    val installedTimeProvider: InstalledTimeProvider,
    val firstOpenTimeProvider: FirstOpenTimeProvider,
    val installedHourProvider: InstalledHourProvider,
    val firstOpenHourProvider: FirstOpenHourProvider,
    val installFirstEventProvider: InstallFirstEventProvider,
    val installBeginTimeProvider: InstallBeginTimeProvider,
    val installFinishTimeProvider: InstallFinishTimeProvider,
    val referrerInstallVersionProvider: ReferrerInstallVersionProvider,
    val referralTimeProvider: ReferralTimeProvider,
    val referrerClickTimestampProvider: ReferrerClickTimestampProvider,
    val referrerClickTimestampServerProvider: ReferrerClickTimestampServerProvider,
    val referrerGooglePlayInstantProvider: ReferrerGooglePlayInstantProvider,
    val createdTimeProvider: CreatedTimeProvider,
    val createdTimeMilliProvider: CreatedTimeMilliProvider,
    val createdTimeHourProvider: CreatedTimeHourProvider,
    val lastSessionTimeProvider: LastSessionTimeProvider,
    val cpuTypeProvider: CpuTypeProvider,
    val hardwareNameProvider: HardwareNameProvider,
    val deviceManufacturerProvider: DeviceManufacturerProvider,
    val deeplinkClickProvider: DeeplinkClickPropertyProvider,
    val deviceAtlasIdProvider: String, // DeviceAtlasIdProvider, todo
    val affDeviceIdProvider: AffiseDeviceIdProvider,
    val affaltDeviceIdProvider: AffiseAltDeviceIdProvider,
    val adidProvider: String, //AdidProvider, todo
    val androidIdProvider: AndroidIdProvider,
    val androidIdMd5Provider: AndroidIdMD5Provider,
    val altstrAdidProvider: String, //AltstrAdidProvider, todo
    val fireosAdidProvider: String, //FireosAdidProvider, todo
    val colorosAdidProvider: String, //ColorosAdidProvider, todo
    val reftokenProvider: RefTokenProvider,
    val reftokensProvider: RefTokensProvider,
    val referrerProvider: InstallReferrerProvider,
    val userAgentProvider: UserAgentProvider,
    val mccodeProvider: MCCProvider,
    val mncodeProvider: MNCProvider,
    val regionProvider: RegionProvider,
    val countryProvider: CountryProvider,
    val languageProvider: LanguageProvider,
    val deviceNameProvider: DeviceNameProvider,
    val deviceTypeProvider: DeviceTypeProvider,
    val osNameProvider: OsNameProvider,
    val platformProvider: PlatformNameProvider,
    val sdkPlatformProvider: SdkPlatformNameProvider,
    val apiLevelOsProvider: ApiLevelOSProvider,
    val affSdkVersionProvider: AffSDKVersionProvider,
    val osVersionProvider: OSVersionProvider,
    val randomUserIdProvider: RandomUserIdProvider,
    val affSdkPosProvider: IsProductionPropertyProvider,
    val timezoneDevProvider: TimezoneDeviceProvider,
    val affEventTokenProvider: String, //: AffiseEventTokenProvider, //todo hold
    val affEventNameProvider: String, //: AffiseEventNameProvider, todo
    val lastTimeSessionProvider: LastSessionTimeProvider,
    val timeSessionProvider: TimeSessionProvider,
    val affSessionCountProvider: AffiseSessionCountProvider,
    val lifetimeSessionCountProvider: LifetimeSessionCountProvider,
    val affDeeplinkProvider: DeeplinkProvider,
    val affpartParamNameProvider: AffPartParamNamePropertyProvider,
    val affpartParamNameTokenProvider: AffPartParamNameTokenPropertyProvider,
    val affAppTokenProvider: AffAppTokenPropertyProvider,
    val labelProvider: String,  //LabelProvider, todo hold
    val affsdkSecretIdProvider: AffSDKSecretIdProvider,
    val pushtokenProvider: PushTokenProvider,
) {

    /**
     * Create PostBackModel with [events] and [logs]
     *
     * @return PostBackModel
     */
    fun create(
        events: List<SerializedEvent> = emptyList(),
        logs: List<SerializedLog> = emptyList(),
        metrics: List<SerializedEvent> = emptyList(),
        internalEvents: List<SerializedEvent> = emptyList(),
    ): PostBackModel {
        val createdTime = createdTimeProvider.provideWithDefault()

        return PostBackModel(
            uuid = uuidProvider.provideWithDefault(),
            affiseAppId = affiseAppIdProvider.provideWithDefault(),
            affisePkgAppName = affisePkgAppNameProvider.provideWithDefault(),
            appVersion = appVersionProvider.provideWithDefault(),
            appVersionRaw = appVersionRawProvider.provideWithDefault(),
            store = storeProvider.provideWithDefault(),
            installedTime = installedTimeProvider.provideWithDefault(),
            firstOpenTime = firstOpenTimeProvider.provideWithDefault(),
            installedHour = installedHourProvider.provideWithDefault(),
            firstOpenHour = firstOpenHourProvider.provideWithDefault(),
            installFirstEvent = installFirstEventProvider.provideWithDefault(),
            installBeginTime = installBeginTimeProvider.provideWithDefault(),
            installFinishTime = installFinishTimeProvider.provideWithDefault(),
            referrerInstallVersion = referrerInstallVersionProvider.provideWithDefault(),
            referralTime = referralTimeProvider.provideWithDefault(),
            referrerClickTimestamp = referrerClickTimestampProvider.provideWithDefault(),
            referrerClickTimestampServer = referrerClickTimestampServerProvider.provideWithDefault(),
            referrerGooglePlayInstant = referrerGooglePlayInstantProvider.provideWithDefault(),
            createdTime = createdTime,
            createdTimeMilli = createdTimeMilliProvider.provideWithDefault(),
            createdTimeHour = createdTimeHourProvider.provideWithDefault(),
            lastSessionTime = lastSessionTimeProvider.provideWithDefault(),
            cpuType = cpuTypeProvider.provideWithDefault(),
            hardwareName = hardwareNameProvider.provideWithDefault(),
            deviceManufacturer = deviceManufacturerProvider.provideWithDefault(),
            deeplinkClick = deeplinkClickProvider.provideWithDefault(),
            deviceAtlasId = deviceAtlasIdProvider,// TODO .provideWithDefault(),
            affDeviceId = affDeviceIdProvider.provideWithDefault(),
            affaltDeviceId = affaltDeviceIdProvider.provideWithDefault(),
            adid = adidProvider,// TODO .provideWithDefault(),
            androidId = androidIdProvider.provideWithDefault(),
            androidIdMd5 = androidIdMd5Provider.provideWithDefault(),
            altstrAdid = altstrAdidProvider,// TODO .provideWithDefault(),
            fireosAdid = fireosAdidProvider,// TODO .provideWithDefault(),
            colorosAdid = colorosAdidProvider,// TODO .provideWithDefault(),
            reftoken = reftokenProvider.provideWithDefault(),
            reftokens = reftokensProvider.provideWithDefault(),
            referrer = referrerProvider.provideWithDefault(),
            userAgent = userAgentProvider.provideWithDefault(),
            mccode = mccodeProvider.provideWithDefault(),
            mncode = mncodeProvider.provideWithDefault(),
            region = regionProvider.provideWithDefault(),
            country = countryProvider.provideWithDefault(),
            language = languageProvider.provideWithDefault(),
            deviceName = deviceNameProvider.provideWithDefault(),
            deviceType = deviceTypeProvider.provideWithDefault(),
            osName = osNameProvider.provideWithDefault(),
            platform = platformProvider.provideWithDefault(),
            sdkPlatform = sdkPlatformProvider.provideWithDefault(),
            apiLevelOs = apiLevelOsProvider.provideWithDefault(),
            affSdkVersion = affSdkVersionProvider.provideWithDefault(),
            osVersion = osVersionProvider.provideWithDefault(),
            randomUserId = randomUserIdProvider.provideWithDefault(),
            affSdkPos = affSdkPosProvider.provideWithDefault(),
            timezoneDev = timezoneDevProvider.provideWithDefault(),
            affEventToken = affEventTokenProvider,// TODO .provideWithDefault(),
            affEventName = affEventNameProvider,// TODO .provideWithDefault(),
            lastTimeSession = lastTimeSessionProvider.provide()
                ?.takeIf { it > 0 }
                ?: firstOpenTimeProvider.provideWithDefault(),
            timeSession = timeSessionProvider.provideWithDefault(),
            affSessionCount = affSessionCountProvider.provideWithDefault(),
            lifetimeSessionCount = lifetimeSessionCountProvider.provideWithDefault(),
            affDeeplink = affDeeplinkProvider.provideWithDefault(),
            affpartParamName = affpartParamNameProvider.provideWithDefault(),
            affpartParamNameToken = affpartParamNameTokenProvider.provideWithDefault(),
            affAppToken = affAppTokenProvider.provideWithParamAndDefault(createdTime.toString()),
            label = labelProvider,// TODO .provideWithDefault(),
            affsdkSecretId = affsdkSecretIdProvider.provideWithDefault(),
            pushtoken = pushtokenProvider.provideWithDefault(),
            events = events,
            logs = logs,
            metrics = metrics,
            internalEvents = internalEvents,
        )
    }
}