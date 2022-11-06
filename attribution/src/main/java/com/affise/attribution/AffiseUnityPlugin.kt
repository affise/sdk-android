package com.affise.attribution

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.affise.attribution.advertising.AdvertisingIdManager
import com.affise.attribution.advertising.AdvertisingIdManagerImpl
import com.affise.attribution.advertising.GoogleIdentifierConnection
import com.affise.attribution.build.BuildConfigPropertiesProviderImpl
import com.affise.attribution.converter.StringToAffiseReferrerDataConverter
import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.affise.attribution.deeplink.DeeplinkClickRepositoryImpl
import com.affise.attribution.deeplink.DeeplinkManagerImpl
import com.affise.attribution.deeplink.InstallReferrerToDeeplinkUriConverter
import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.init.InitPropertiesStorageImpl
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.referrer.AffiseReferrerDataToStringConverter
import com.affise.attribution.unity.UnityLogsManager
import com.affise.attribution.unity.UnityPropertiesProviderFactory
import com.affise.attribution.unity.UnityProvidersModel
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import com.affise.attribution.utils.ActivityActionsManager
import com.affise.attribution.utils.ActivityActionsManagerImpl
import com.affise.attribution.utils.EncryptedSharedPreferences

class AffiseUnityPlugin(app: Application) : AffiseUnityPluginApi {

    private val logsManager: LogsManager = UnityLogsManager()

    private val initPropertiesStorage: InitPropertiesStorage = InitPropertiesStorageImpl()

    private val isDeeplinkClickRepository: DeeplinkClickRepository = DeeplinkClickRepositoryImpl()

    private val activityActionsManager: ActivityActionsManager by lazy {
        ActivityActionsManagerImpl(app, logsManager)
    }

    private val advertisingIdManager: AdvertisingIdManager by lazy {
        AdvertisingIdManagerImpl(
            GoogleIdentifierConnection(),
            ExecutorServiceProviderImpl("GAID Worker"),
            logsManager
        )
    }

    private val sharedPreferences: SharedPreferences by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            EncryptedSharedPreferences(app, PREFERENCES_ENCRYPTED_FILE_NAME)
        } else {
            app.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        }
    }
    private val deeplinkManager: DeeplinkManagerImpl by lazy {
        DeeplinkManagerImpl(
            initPropertiesStorage,
            isDeeplinkClickRepository,
            activityActionsManager
        )
    }

    private val retrieveInstallReferrerUseCase by lazy {
        RetrieveInstallReferrerUseCase(
            sharedPreferences,
            AffiseReferrerDataToStringConverter(),
            StringToAffiseReferrerDataConverter(logsManager),
            app,
            deeplinkManager,
            logsManager,
            InstallReferrerToDeeplinkUriConverter()
        )
    }

    private val providers: UnityProvidersModel by lazy {
        UnityPropertiesProviderFactory(
            app = app,
            logsManager = logsManager,
            buildConfigPropertiesProvider = BuildConfigPropertiesProviderImpl(),
            retrieveInstallReferrerUseCase = retrieveInstallReferrerUseCase,
            advertisingIdManager = advertisingIdManager,
        ).create()
    }

    init {
        advertisingIdManager.init(app)
        retrieveInstallReferrerUseCase.startInstallReferrerRetrieve()
        deeplinkManager.init()
    }

    override fun getInstalledTime(): Long {
        return providers.installedTimeProvider.provideWithDefault()
    }

    override fun getInstalledBeginTime(): Long {
        return providers.installBeginTimeProvider.provideWithDefault()
    }

    override fun getApiVersion(): String {
        return providers.apiLevelOSProvider.provideWithDefault()
    }

    override fun getOSVersion(): String {
        return providers.osVersionProvider.provideWithDefault()
    }

    override fun getIsp(): String {
        return providers.ispNameProvider.provideWithDefault()
    }

    override fun getAndroidId(): String {
        return providers.androidIdProvider.provideWithDefault()
    }

    override fun getDeviceManufacturer(): String {
        return providers.deviceManufacturerProvider.provideWithDefault()
    }

    override fun getConnectionType(): String {
        return providers.connectionTypeProvider.provideWithDefault()
    }

    override fun getNetworkType(): String {
        return providers.networkTypeProvider.provideWithDefault()
    }

    override fun getStore(): String {
        return providers.storeProvider.provideWithDefault()
    }

    override fun getGaidAdid(): String {
        return providers.gaidAdidProvider.provideWithDefault()
    }

    override fun getReferrer(): String {
        return providers.referrerProvider.provideWithDefault()
    }

    override fun getReferrerInstallVersion(): String {
        return providers.referrerInstallVersionProvider.provideWithDefault()
    }

    override fun getReferrerClickTimestamp(): Long {
        return providers.referrerClickTimestampProvider.provideWithDefault()
    }

    override fun getReferrerClickTimestampServer(): Long {
        return providers.referrerClickTimestampServerProvider.provideWithDefault()
    }

    override fun getReferrerGooglePlayInstant(): Boolean {
        return providers.referrerGooglePlayInstantProvider.provideWithDefault()
    }

    companion object {
        private const val PREFERENCES_FILE_NAME = "com.affise.attribution"
        private const val PREFERENCES_ENCRYPTED_FILE_NAME = "com.affise.attribution.encrypted"
    }
}