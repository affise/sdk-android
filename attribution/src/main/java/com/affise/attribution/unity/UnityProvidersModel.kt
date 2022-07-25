package com.affise.attribution.unity

import com.affise.attribution.parameters.*

internal class UnityProvidersModel(
    val storeProvider: StoreProvider,
    val installedTimeProvider: InstalledTimeProvider,
    val connectionTypeProvider: ConnectionTypeProvider,
    val networkTypeProvider: NetworkTypeProvider,
    val deviceManufacturerProvider: DeviceManufacturerProvider,
    val androidIdProvider: AndroidIdProvider,
    val ispNameProvider: IspNameProvider,
    val apiLevelOSProvider: ApiLevelOSProvider,
    val osVersionProvider: OSVersionProvider,
    val installBeginTimeProvider: InstallBeginTimeProvider,
    val gaidAdidProvider: GoogleAdvertisingIdProvider,
    val referrerProvider: InstallReferrerProvider,
)