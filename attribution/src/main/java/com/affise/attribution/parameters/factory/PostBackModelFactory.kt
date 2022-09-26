package com.affise.attribution.parameters.factory

import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.SerializedLog
import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.AffAppTokenPropertyProvider
import com.affise.attribution.parameters.AffPartParamNamePropertyProvider
import com.affise.attribution.parameters.AffPartParamNameTokenPropertyProvider
import com.affise.attribution.parameters.AffSDKSecretIdProvider
import com.affise.attribution.parameters.AffSDKVersionProvider
import com.affise.attribution.parameters.AffiseAltDeviceIdProvider
import com.affise.attribution.parameters.AffiseAppIdProvider
import com.affise.attribution.parameters.AffiseDeviceIdProvider
import com.affise.attribution.parameters.AffisePackageAppNameProvider
import com.affise.attribution.parameters.AffiseSessionCountProvider
import com.affise.attribution.parameters.AndroidIdMD5Provider
import com.affise.attribution.parameters.AndroidIdProvider
import com.affise.attribution.parameters.ApiLevelOSProvider
import com.affise.attribution.parameters.AppVersionProvider
import com.affise.attribution.parameters.AppVersionRawProvider
import com.affise.attribution.parameters.ConnectionTypeProvider
import com.affise.attribution.parameters.CountryProvider
import com.affise.attribution.parameters.CpuTypeProvider
import com.affise.attribution.parameters.CreatedTimeHourProvider
import com.affise.attribution.parameters.CreatedTimeMilliProvider
import com.affise.attribution.parameters.CreatedTimeProvider
import com.affise.attribution.parameters.DeeplinkClickPropertyProvider
import com.affise.attribution.parameters.DeeplinkProvider
import com.affise.attribution.parameters.DeviceManufacturerProvider
import com.affise.attribution.parameters.DeviceNameProvider
import com.affise.attribution.parameters.DeviceTypeProvider
import com.affise.attribution.parameters.FirstOpenHourProvider
import com.affise.attribution.parameters.FirstOpenTimeProvider
import com.affise.attribution.parameters.GoogleAdvertisingIdMd5Provider
import com.affise.attribution.parameters.GoogleAdvertisingIdProvider
import com.affise.attribution.parameters.HardwareNameProvider
import com.affise.attribution.parameters.InstallBeginTimeProvider
import com.affise.attribution.parameters.InstallFinishTimeProvider
import com.affise.attribution.parameters.InstallReferrerProvider
import com.affise.attribution.parameters.InstalledHourProvider
import com.affise.attribution.parameters.InstalledTimeProvider
import com.affise.attribution.parameters.IsProductionPropertyProvider
import com.affise.attribution.parameters.IspNameProvider
import com.affise.attribution.parameters.LanguageProvider
import com.affise.attribution.parameters.LastSessionTimeProvider
import com.affise.attribution.parameters.LifetimeSessionCountProvider
import com.affise.attribution.parameters.MCCProvider
import com.affise.attribution.parameters.MNCProvider
import com.affise.attribution.parameters.MacMD5Provider
import com.affise.attribution.parameters.MacSha1Provider
import com.affise.attribution.parameters.NetworkTypeProvider
import com.affise.attribution.parameters.OSVersionProvider
import com.affise.attribution.parameters.OaidMD5Provider
import com.affise.attribution.parameters.OaidProvider
import com.affise.attribution.parameters.OsNameProvider
import com.affise.attribution.parameters.PlatformNameProvider
import com.affise.attribution.parameters.ProxyIpAddressProvider
import com.affise.attribution.parameters.PushTokenProvider
import com.affise.attribution.parameters.RandomUserIdProvider
import com.affise.attribution.parameters.RefTokenProvider
import com.affise.attribution.parameters.RefTokensProvider
import com.affise.attribution.parameters.ReferralTimeProvider
import com.affise.attribution.parameters.RegionProvider
import com.affise.attribution.parameters.StoreProvider
import com.affise.attribution.parameters.TimeSessionProvider
import com.affise.attribution.parameters.TimezoneDeviceProvider
import com.affise.attribution.parameters.UserAgentProvider
import com.affise.attribution.parameters.UuidProvider

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
    val installBeginTimeProvider: InstallBeginTimeProvider,
    val installFinishTimeProvider: InstallFinishTimeProvider,
    val referralTimeProvider: ReferralTimeProvider,
    val createdTimeProvider: CreatedTimeProvider,
    val createdTimeMilliProvider: CreatedTimeMilliProvider,
    val createdTimeHourProvider: CreatedTimeHourProvider,
    val lastSessionTimeProvider: LastSessionTimeProvider,
    val connectionTypeProvider: ConnectionTypeProvider,
    val cpuTypeProvider: CpuTypeProvider,
    val hardwareNameProvider: HardwareNameProvider,
    val networkTypeProvider: NetworkTypeProvider,
    val deviceManufacturerProvider: DeviceManufacturerProvider,
    val proxyIpAddressProvider: ProxyIpAddressProvider,
    val deeplinkClickProvider: DeeplinkClickPropertyProvider,
    val deviceAtlasIdProvider: String, // DeviceAtlasIdProvider, todo
    val affDeviceIdProvider: AffiseDeviceIdProvider,
    val affaltDeviceIdProvider: AffiseAltDeviceIdProvider,
    val adidProvider: String, //AdidProvider, todo
    val androidIdProvider: AndroidIdProvider,
    val androidIdMd5Provider: AndroidIdMD5Provider,
    val macSha1Provider: MacSha1Provider,
    val macMd5Provider: MacMD5Provider,
    val gaidAdidProvider: GoogleAdvertisingIdProvider,
    val gaidAdidMd5Provider: GoogleAdvertisingIdMd5Provider,
    val oaidProvider: OaidProvider,
    val oaidMd5Provider: OaidMD5Provider,
    val altstrAdidProvider: String, //AltstrAdidProvider, todo
    val fireosAdidProvider: String, //FireosAdidProvider, todo
    val colorosAdidProvider: String, //ColorosAdidProvider, todo
    val reftokenProvider: RefTokenProvider,
    val reftokensProvider: RefTokensProvider,
    val referrerProvider: InstallReferrerProvider,
    val userAgentProvider: UserAgentProvider,
    val mccodeProvider: MCCProvider,
    val mncodeProvider: MNCProvider,
    val ispProvider: IspNameProvider,
    val regionProvider: RegionProvider,
    val countryProvider: CountryProvider,
    val languageProvider: LanguageProvider,
    val deviceNameProvider: DeviceNameProvider,
    val deviceTypeProvider: DeviceTypeProvider,
    val osNameProvider: OsNameProvider,
    val platformProvider: PlatformNameProvider,
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
        metrics: List<SerializedEvent> = emptyList()
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
            installBeginTime = installBeginTimeProvider.provideWithDefault(),
            installFinishTime = installFinishTimeProvider.provideWithDefault(),
            referralTime = referralTimeProvider.provideWithDefault(),
            createdTime = createdTime,
            createdTimeMilli = createdTimeMilliProvider.provideWithDefault(),
            createdTimeHour = createdTimeHourProvider.provideWithDefault(),
            lastSessionTime = lastSessionTimeProvider.provideWithDefault(),
            connectionType = connectionTypeProvider.provideWithDefault(),
            cpuType = cpuTypeProvider.provideWithDefault(),
            hardwareName = hardwareNameProvider.provideWithDefault(),
            networkType = networkTypeProvider.provideWithDefault(),
            deviceManufacturer = deviceManufacturerProvider.provideWithDefault(),
            proxyIpAddress = proxyIpAddressProvider.provideWithDefault(),
            deeplinkClick = deeplinkClickProvider.provideWithDefault(),
            deviceAtlasId = deviceAtlasIdProvider,// TODO .provideWithDefault(),
            affDeviceId = affDeviceIdProvider.provideWithDefault(),
            affaltDeviceId = affaltDeviceIdProvider.provideWithDefault(),
            adid = adidProvider,// TODO .provideWithDefault(),
            androidId = androidIdProvider.provideWithDefault(),
            androidIdMd5 = androidIdMd5Provider.provideWithDefault(),
            macSha1 = macSha1Provider.provideWithDefault(),
            macMd5 = macMd5Provider.provideWithDefault(),
            gaidAdid = gaidAdidProvider.provideWithDefault(),
            gaidAdidMd5 = gaidAdidMd5Provider.provideWithDefault(),
            oaid = oaidProvider.provideWithDefault(),
            oaidMd5 = oaidMd5Provider.provideWithDefault(),
            altstrAdid = altstrAdidProvider,// TODO .provideWithDefault(),
            fireosAdid = fireosAdidProvider,// TODO .provideWithDefault(),
            colorosAdid = colorosAdidProvider,// TODO .provideWithDefault(),
            reftoken = reftokenProvider.provideWithDefault(),
            reftokens = reftokensProvider.provideWithDefault(),
            referrer = referrerProvider.provideWithDefault(),
            userAgent = userAgentProvider.provideWithDefault(),
            mccode = mccodeProvider.provideWithDefault(),
            mncode = mncodeProvider.provideWithDefault(),
            isp = ispProvider.provideWithDefault(),
            region = regionProvider.provideWithDefault(),
            country = countryProvider.provideWithDefault(),
            language = languageProvider.provideWithDefault(),
            deviceName = deviceNameProvider.provideWithDefault(),
            deviceType = deviceTypeProvider.provideWithDefault(),
            osName = osNameProvider.provideWithDefault(),
            platform = platformProvider.provideWithDefault(),
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
        )
    }
}