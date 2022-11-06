package com.affise.attribution.unity

import android.app.Application
import com.affise.attribution.advertising.AdvertisingIdManager
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.*
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import com.affise.attribution.utils.SystemAppChecker

internal class UnityPropertiesProviderFactory(
    private val app: Application,
    private val logsManager: LogsManager,
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider,
    private val retrieveInstallReferrerUseCase: RetrieveInstallReferrerUseCase,
    private val advertisingIdManager: AdvertisingIdManager,
) {
    fun create(): UnityProvidersModel = UnityProvidersModel(
        storeProvider = StoreProvider(app, logsManager, SystemAppChecker(app)),
        installedTimeProvider = InstalledTimeProvider(app, logsManager),
        installBeginTimeProvider = InstallBeginTimeProvider(retrieveInstallReferrerUseCase),
        connectionTypeProvider = ConnectionTypeProvider(app),
        networkTypeProvider = NetworkTypeProvider(app),
        deviceManufacturerProvider = DeviceManufacturerProvider(buildConfigPropertiesProvider),
        androidIdProvider = AndroidIdProvider(app),
        ispNameProvider = IspNameProvider(app),
        apiLevelOSProvider = ApiLevelOSProvider(buildConfigPropertiesProvider),
        osVersionProvider = OSVersionProvider(buildConfigPropertiesProvider),
        gaidAdidProvider = GoogleAdvertisingIdProvider(advertisingIdManager),
        referrerProvider = InstallReferrerProvider(app, retrieveInstallReferrerUseCase),
        referrerInstallVersionProvider = ReferrerInstallVersionProvider(retrieveInstallReferrerUseCase),
        referrerClickTimestampProvider = ReferrerClickTimestampProvider(retrieveInstallReferrerUseCase),
        referrerClickTimestampServerProvider = ReferrerClickTimestampServerProvider(retrieveInstallReferrerUseCase),
        referrerGooglePlayInstantProvider = ReferrerGooglePlayInstantProvider(retrieveInstallReferrerUseCase),
    )
}