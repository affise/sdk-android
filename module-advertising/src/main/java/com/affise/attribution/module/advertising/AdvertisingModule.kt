package com.affise.attribution.module.advertising

import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.converter.StringToMD5Converter
import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.providers.EmptyStringProvider
import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.module.advertising.advertising.AdvertisingIdManager
import com.affise.attribution.module.advertising.advertising.AdvertisingIdManagerImpl
import com.affise.attribution.module.advertising.oaid.OaidManager
import com.affise.attribution.module.advertising.oaid.OaidManagerImpl
import com.affise.attribution.module.advertising.parameters.*
import com.affise.attribution.parameters.ProviderType

class AdvertisingModule : AffiseModule() {

    private var advManager: AdvertisingIdManager? = null
    private var oaidManager: OaidManager? = null
    private var stringToMD5Converter: StringToMD5Converter? = null

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

    override fun init(logsManager: LogsManager) {
        advManager = AdvertisingIdManagerImpl(
            ExecutorServiceProviderImpl("GAID Worker"),
            logsManager
        )
        get<BuildConfigPropertiesProvider>()?.let { buildConfigPropertiesProvider ->
            oaidManager = OaidManagerImpl(
                logsManager,
                ExecutorServiceProviderImpl("OAID Worker"),
                buildConfigPropertiesProvider
            )
        }

        stringToMD5Converter = get<StringToMD5Converter>()

        application?.let {
            advManager?.init(it)
            oaidManager?.init(it)
        }
    }

    override fun providers(): List<PropertyProvider<*>> = listOfNotNull(
        googleAdvertisingIdProvider,
        googleAdvertisingIdMd5Provider,
        oaidProvider,
        oaidMD5Provider,
        EmptyStringProvider(ProviderType.ADID, 29.0f),
        EmptyStringProvider(ProviderType.ALTSTR_ADID, 31.7f),
        EmptyStringProvider(ProviderType.FIREOS_ADID, 31.8f),
        EmptyStringProvider(ProviderType.COLOROS_ADID, 31.9f),
    )
}