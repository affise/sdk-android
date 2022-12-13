package com.affise.attribution

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.affise.attribution.advertising.AdvertisingIdManager
import com.affise.attribution.advertising.AdvertisingIdManagerImpl
import com.affise.attribution.advertising.GoogleIdentifierConnection
import com.affise.attribution.build.BuildConfigPropertiesProviderImpl
import com.affise.attribution.converter.Converter
import com.affise.attribution.converter.ConverterToBase64
import com.affise.attribution.converter.JsonObjectToMetricsEventConverter
import com.affise.attribution.converter.LogToSerializedLogConverter
import com.affise.attribution.converter.PostBackModelToJsonStringConverter
import com.affise.attribution.converter.StringToAffiseReferrerDataConverter
import com.affise.attribution.converter.StringToMD5Converter
import com.affise.attribution.converter.StringToSHA1Converter
import com.affise.attribution.converter.StringToSHA256Converter
import com.affise.attribution.deeplink.DeeplinkClickRepository
import com.affise.attribution.deeplink.DeeplinkClickRepositoryImpl
import com.affise.attribution.deeplink.DeeplinkManagerImpl
import com.affise.attribution.deeplink.InstallReferrerToDeeplinkUriConverter
import com.affise.attribution.events.Event
import com.affise.attribution.events.EventToSerializedEventConverter
import com.affise.attribution.events.EventsManager
import com.affise.attribution.events.EventsRepository
import com.affise.attribution.events.EventsRepositoryImpl
import com.affise.attribution.events.GDPREventRepository
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.events.StoreEventUseCase
import com.affise.attribution.events.StoreEventUseCaseImpl
import com.affise.attribution.events.autoCatchingClick.AutoCatchingClickProvider
import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.init.AffiseInitProperties
import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.init.InitPropertiesStorageImpl
import com.affise.attribution.init.SetPropertiesWhenAppInitializedUseCase
import com.affise.attribution.init.SetPropertiesWhenAppInitializedUseCaseImpl
import com.affise.attribution.logs.AffiseThreadUncaughtExceptionHandlerImpl
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.logs.LogsManagerImpl
import com.affise.attribution.logs.LogsRepository
import com.affise.attribution.logs.LogsRepositoryImpl
import com.affise.attribution.logs.StoreLogsUseCase
import com.affise.attribution.logs.StoreLogsUseCaseImpl
import com.affise.attribution.metrics.MetricsManager
import com.affise.attribution.metrics.MetricsManagerImpl
import com.affise.attribution.metrics.MetricsRepository
import com.affise.attribution.metrics.MetricsRepositoryImpl
import com.affise.attribution.metrics.MetricsUseCase
import com.affise.attribution.metrics.MetricsUseCaseImpl
import com.affise.attribution.network.CloudRepository
import com.affise.attribution.network.CloudRepositoryImpl
import com.affise.attribution.network.HttpClientImpl
import com.affise.attribution.oaid.OaidManager
import com.affise.attribution.oaid.OaidManagerImpl
import com.affise.attribution.parameters.InstallReferrerProvider
import com.affise.attribution.parameters.base.PropertiesProviderFactory
import com.affise.attribution.parameters.factory.PostBackModelFactory
import com.affise.attribution.preferences.ApplicationLifecyclePreferencesRepositoryImpl
import com.affise.attribution.preferences.ApplicationLifetimePreferencesRepositoryImpl
import com.affise.attribution.referrer.AffiseReferrerDataToStringConverter
import com.affise.attribution.session.CurrentActiveActivityCountProvider
import com.affise.attribution.session.CurrentActiveActivityCountProviderImpl
import com.affise.attribution.session.SessionManager
import com.affise.attribution.session.SessionManagerImpl
import com.affise.attribution.storages.EventsStorage
import com.affise.attribution.storages.EventsStorageImpl
import com.affise.attribution.storages.LogsStorage
import com.affise.attribution.storages.LogsStorageImpl
import com.affise.attribution.storages.MetricsStorage
import com.affise.attribution.storages.MetricsStorageImpl
import com.affise.attribution.test.CrashApplicationUseCase
import com.affise.attribution.test.CrashApplicationUseCaseImpl
import com.affise.attribution.usecase.EraseUserDataUseCaseImpl
import com.affise.attribution.usecase.FirstAppOpenUseCase
import com.affise.attribution.usecase.PreferencesUseCaseImpl
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import com.affise.attribution.usecase.SendDataToServerUseCase
import com.affise.attribution.usecase.SendDataToServerUseCaseImpl
import com.affise.attribution.usecase.SendGDPREventUseCaseImpl
import com.affise.attribution.utils.ActivityActionsManager
import com.affise.attribution.utils.ActivityActionsManagerImpl
import com.affise.attribution.utils.EncryptedSharedPreferences
import com.affise.attribution.webBridge.WebBridgeManager

internal class AffiseComponent(
    private val app: Application,
    initProperties: AffiseInitProperties,
) : AffiseApi {

    /**
     * PostBackModelFactory
     */
    private val postBackModelFactory: PostBackModelFactory by lazy {
        PropertiesProviderFactory(
            advertisingIdManager,
            BuildConfigPropertiesProviderImpl(),
            app,
            firstAppOpenUseCase,
            oaidManager,
            retrieveInstallReferrerUseCase,
            sessionManager,
            sharedPreferences,
            initPropertiesStorage,
            StringToMD5Converter(logsManager),
            StringToSHA1Converter(),
            StringToSHA256Converter(),
            logsManager,
            isDeeplinkClickRepository,
            installReferrerProvider
        ).create()
    }

    private val converterToBase64: ConverterToBase64 by lazy {
        ConverterToBase64()
    }

    /**
     * ActivityCountProvider
     */
    private val activityCountProvider: CurrentActiveActivityCountProvider by lazy {
        CurrentActiveActivityCountProviderImpl(activityActionsManager)
    }

    /**
     * Provides [EventsStorage]
     */
    private val eventsStorage: EventsStorage by lazy {
        EventsStorageImpl(app, logsManager)
    }

    /**
     * Provides [Converter] from [Event] to [SerializedEvent]
     */
    private val eventToSerializedEventConverter: Converter<Event, SerializedEvent> by lazy {
        EventToSerializedEventConverter()
    }

    /**
     * EventsRepository
     */
    private val eventsRepository: EventsRepository by lazy {
        EventsRepositoryImpl(
            converterToBase64,
            eventToSerializedEventConverter,
            logsManager,
            eventsStorage
        )
    }

    private val logStorage: LogsStorage by lazy {
        LogsStorageImpl(app)
    }

    /**
     * LogsRepository
     */
    private val logsRepository: LogsRepository by lazy {
        LogsRepositoryImpl(converterToBase64, LogToSerializedLogConverter(), logStorage)
    }

    private val metricsStorage: MetricsStorage by lazy {
        MetricsStorageImpl(app, JsonObjectToMetricsEventConverter())
    }

    /**
     * MetricsRepository
     */
    private val metricsRepository: MetricsRepository by lazy {
        MetricsRepositoryImpl(
            converterToBase64,
            eventToSerializedEventConverter,
            metricsStorage
        )
    }

    /**
     * MetricsUseCase
     */
    private val metricsUseCase: MetricsUseCase by lazy {
        MetricsUseCaseImpl(ExecutorServiceProviderImpl("Metrics Worker"), metricsRepository)
    }

    /**
     * MetricsManager
     */
    override val metricsManager: MetricsManager by lazy {
        MetricsManagerImpl(activityActionsManager, metricsUseCase, StringToSHA1Converter())
    }

    /**
     * Provides [CrashApplicationUseCase]
     */
    override val crashApplicationUseCase: CrashApplicationUseCase by lazy {
        CrashApplicationUseCaseImpl()
    }

    /**
     * CloudRepository
     */
    private val cloudRepository: CloudRepository by lazy {
        CloudRepositoryImpl(
            HttpClientImpl(),
            postBackModelFactory.userAgentProvider,
            PostBackModelToJsonStringConverter()
        )
    }

    private val gdprEventRepository: GDPREventRepository by lazy {
        GDPREventRepository(sharedPreferences, eventToSerializedEventConverter)
    }

    /**
     * SendDataToServerUseCase
     */
    private val sendDataToServerUseCase: SendDataToServerUseCase by lazy {
        SendDataToServerUseCaseImpl(
            postBackModelFactory,
            cloudRepository,
            eventsRepository,
            ExecutorServiceProviderImpl("Sending Worker"),
            logsRepository,
            metricsRepository,
            logsManager,
            preferencesUseCase
        )
    }

    /**
     * StoreLogsUseCase
     */
    private val storeLogsUseCase: StoreLogsUseCase by lazy {
        StoreLogsUseCaseImpl(
            ExecutorServiceProviderImpl("Log Worker"),
            logsRepository
        )
    }

    /**
     * SetPropertiesWhenInitUseCase
     */
    override val setPropertiesWhenInitUseCase: SetPropertiesWhenAppInitializedUseCase by lazy {
        SetPropertiesWhenAppInitializedUseCaseImpl(initPropertiesStorage)
    }

    /**
     * FirstAppOpenUseCase
     */
    override val firstAppOpenUseCase: FirstAppOpenUseCase by lazy {
        FirstAppOpenUseCase(sharedPreferences, activityCountProvider)
    }

    /**
     * StoreEventUseCase
     */
    override val storeEventUseCase: StoreEventUseCase by lazy {
        StoreEventUseCaseImpl(
            ExecutorServiceProviderImpl("Event Worker"),
            eventsRepository,
            eventsManager,
            preferencesUseCase,
            activityCountProvider,
            logsManager
        )
    }

    /**
     * RetrieveInstallReferrerUseCase
     */
    override val retrieveInstallReferrerUseCase by lazy {
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

    override val installReferrerProvider by lazy {
        InstallReferrerProvider(app, retrieveInstallReferrerUseCase)
    }

    /**
     * InitPropertiesStorage
     */
    override val initPropertiesStorage: InitPropertiesStorage = InitPropertiesStorageImpl()

    /**
     * provides [PreferencesUseCaseImpl]
     */
    override val preferencesUseCase: PreferencesUseCaseImpl by lazy {
        PreferencesUseCaseImpl(
            ApplicationLifecyclePreferencesRepositoryImpl(),
            ApplicationLifetimePreferencesRepositoryImpl(sharedPreferences)
        )
    }

    /**
     * provides [EraseUserDataUseCaseImpl]
     */
    override val eraseUserDataUseCase: EraseUserDataUseCaseImpl by lazy {
        EraseUserDataUseCaseImpl(eventsRepository, gdprEventRepository)
    }

    /**
     * DeeplinkClickRepository
     */
    private val isDeeplinkClickRepository: DeeplinkClickRepository = DeeplinkClickRepositoryImpl()

    /**
     * ActivityActionsListeners
     */
    private val activityActionsManager: ActivityActionsManager by lazy {
        ActivityActionsManagerImpl(app, logsManager)
    }

    /**
     * SharedPreferences
     */
    override val sharedPreferences: SharedPreferences by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            EncryptedSharedPreferences(app, PREFERENCES_ENCRYPTED_FILE_NAME)
        } else {
            app.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        }
    }

    /**
     * LogsManager
     */
    override val logsManager: LogsManager by lazy {
        LogsManagerImpl(storeLogsUseCase)
    }

    /**
     * SessionManager
     */
    override val sessionManager: SessionManager by lazy {
        SessionManagerImpl(sharedPreferences, activityCountProvider)
    }

    /**
     * EventsManager
     */
    override val eventsManager: EventsManager by lazy {
        EventsManager(sendDataToServerUseCase, activityCountProvider)
    }

    /**
     * AdvertisingIdManager
     */
    override val advertisingIdManager: AdvertisingIdManager by lazy {
        AdvertisingIdManagerImpl(
            GoogleIdentifierConnection(),
            ExecutorServiceProviderImpl("GAID Worker"),
            logsManager
        )
    }

    /**
     * OaidManager
     */
    override val oaidManager: OaidManager by lazy {
        OaidManagerImpl(
            logsManager,
            ExecutorServiceProviderImpl("OAID Worker"),
            BuildConfigPropertiesProviderImpl()
        )
    }

    /**
     * WebBridgeManager
     */
    override val webBridgeManager: WebBridgeManager by lazy {
        WebBridgeManager(storeEventUseCase)
    }

    /**
     * DeeplinkManager
     */
    override val deeplinkManager: DeeplinkManagerImpl by lazy {
        DeeplinkManagerImpl(
            initPropertiesStorage,
            isDeeplinkClickRepository,
            activityActionsManager
        )
    }

    /**
     * AutoCatchingClickProvider
     */
    override val autoCatchingClickProvider: AutoCatchingClickProvider = AutoCatchingClickProvider(
        storeEventUseCase, activityActionsManager
    )

    override val sendGDPREventUseCase: SendGDPREventUseCaseImpl by lazy {
        SendGDPREventUseCaseImpl(
            gdprEventRepository,
            ExecutorServiceProviderImpl("GDPR Event Worker"),
            cloudRepository,
            postBackModelFactory,
            eraseUserDataUseCase
        )
    }

    /**
     * Init properties
     */
    init {
        sendGDPREventUseCase.sendForgetMeEvent()
        firstAppOpenUseCase.onAppCreated()
        sessionManager.init()
        retrieveInstallReferrerUseCase.startInstallReferrerRetrieve( onFinished = {
            eventsManager.init()
        })
        setPropertiesWhenInitUseCase.init(initProperties)
        advertisingIdManager.init(app)
        oaidManager.init(app)
        deeplinkManager.init()
        autoCatchingClickProvider.init(initProperties.autoCatchingClickEvents)
        metricsManager.setEnabledMetrics(initProperties.enabledMetrics)

        AffiseThreadUncaughtExceptionHandlerImpl(
            Thread.getDefaultUncaughtExceptionHandler(),
            logsManager
        )
            .also(Thread::setDefaultUncaughtExceptionHandler)
    }

    companion object {
        private const val PREFERENCES_FILE_NAME = "com.affise.attribution"
        private const val PREFERENCES_ENCRYPTED_FILE_NAME = "com.affise.attribution.encrypted"
    }
}