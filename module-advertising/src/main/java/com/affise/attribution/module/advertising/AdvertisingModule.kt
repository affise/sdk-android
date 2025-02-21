package com.affise.attribution.module.advertising

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.converter.StringToMD5Converter
import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.module.advertising.advertising.AdvertisingIdManager
import com.affise.attribution.module.advertising.advertising.AdvertisingIdManagerImpl
import com.affise.attribution.module.advertising.oaid.OaidManager
import com.affise.attribution.module.advertising.oaid.OaidManagerImpl
import com.affise.attribution.module.advertising.parameters.GoogleAdvertisingIdMd5Provider
import com.affise.attribution.module.advertising.parameters.GoogleAdvertisingIdProvider
import com.affise.attribution.module.advertising.parameters.GoogleAdvertisingPersonalizationProvider
import com.affise.attribution.module.advertising.parameters.OaidMD5Provider
import com.affise.attribution.module.advertising.parameters.OaidProvider
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.advertising.AdvertisingApi
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.parameters.providers.EmptyStringProvider

class AdvertisingModule : AffiseModule(), AdvertisingApi {

    override val version: String = BuildConfig.AFFISE_VERSION

    private val stringToMD5Converter: StringToMD5Converter? by lazy {
        get<StringToMD5Converter>()
    }

    private val googleAdvertisingIdProvider: PropertyProvider<*>? by lazy {
        advManager?.let { adv ->
            GoogleAdvertisingIdProvider(adv)
        }
    }

    private val googleAdvertisingIdMd5Provider: PropertyProvider<*>? by lazy {
        stringToMD5Converter?.let { md5 ->
            advManager?.let { adv ->
                GoogleAdvertisingIdMd5Provider(adv, md5)
            }
        }
    }
    private val googleAdvertisingPersonalizationProvider: PropertyProvider<*>? by lazy {
        advManager?.let { adv ->
            GoogleAdvertisingPersonalizationProvider(adv)
        }
    }

    private val oaidProvider: PropertyProvider<*>? by lazy {
        oaidManager?.let { oaid ->
            OaidProvider(oaid)
        }
    }

    private val oaidMD5Provider: PropertyProvider<*>? by lazy {
        oaidManager?.let { oaid ->
            stringToMD5Converter?.let { md5 ->
                OaidMD5Provider(oaid, md5)
            }
        }
    }

    private val advManager: AdvertisingIdManager? by lazy {
        logsManager?.let { logsManager ->
            AdvertisingIdManagerImpl(
                ExecutorServiceProviderImpl("GAID Worker"),
                logsManager
            )
        }
    }

    private val oaidManager: OaidManager? by lazy {
        logsManager?.let { logsManager ->
            get<BuildConfigPropertiesProvider>()?.let { buildConfigPropertiesProvider ->
                OaidManagerImpl(
                    logsManager,
                    ExecutorServiceProviderImpl("OAID Worker"),
                    buildConfigPropertiesProvider
                )
            }
        }
    }

    private var providers: List<PropertyProvider<*>> = emptyList()

    override fun start() {
        application?.let {
            advManager?.init(it)
            oaidManager?.init(it)
        }

        providers = listOfNotNull(
            googleAdvertisingIdProvider,
            googleAdvertisingIdMd5Provider,
            googleAdvertisingPersonalizationProvider,
            oaidProvider,
            oaidMD5Provider,
            EmptyStringProvider(ProviderType.ADID, 29.0f),
            EmptyStringProvider(ProviderType.ALTSTR_ADID, 31.7f),
            EmptyStringProvider(ProviderType.FIREOS_ADID, 31.8f),
            EmptyStringProvider(ProviderType.COLOROS_ADID, 31.9f),
        )
    }

    override fun providers(): List<PropertyProvider<*>> = providers

    override fun getAdvertisingId(): String? = advManager?.getAdvertisingId()
}