package com.affise.attribution.module.network

import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.converter.StringToMD5Converter
import com.affise.attribution.converter.StringToSHA1Converter
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.module.network.parameters.*


class NetworkModule : AffiseModule() {

    private var macProvider: MacProvider? = null
    private var buildConfigPropertiesProvider: BuildConfigPropertiesProvider? = null
    private var stringToMD5Converter: StringToMD5Converter? = null
    private var stringToSHA1Converter: StringToSHA1Converter? = null

    private val connectionTypeProvider: PropertyProvider<*>? by lazy {
        application?.let { app ->
            ConnectionTypeProvider(app)
        }
    }
    private val proxyIpAddressProvider: PropertyProvider<*>? by lazy {
        application?.let { app ->
            buildConfigPropertiesProvider?.let {
                ProxyIpAddressProvider(app, it)
            }
        }
    }

    private val macMD5Provider: PropertyProvider<*>? by lazy {
        macProvider?.let { mac ->
            stringToMD5Converter?.let { md5 ->
                MacMD5Provider(mac, md5)
            }
        }
    }

    private val macSha1Provider: PropertyProvider<*>? by lazy {
        macProvider?.let { mac ->
            stringToSHA1Converter?.let { sha1 ->
                MacSha1Provider(mac, sha1)
            }
        }
    }

    override fun init(logsManager: LogsManager) {
        macProvider = MacProvider(logsManager)
        buildConfigPropertiesProvider = get<BuildConfigPropertiesProvider>()
        stringToMD5Converter = get<StringToMD5Converter>()
        stringToSHA1Converter = get<StringToSHA1Converter>()
    }

    override fun providers(): List<PropertyProvider<*>> = listOfNotNull(
        connectionTypeProvider,
        proxyIpAddressProvider,
        macMD5Provider,
        macSha1Provider,
    )
}