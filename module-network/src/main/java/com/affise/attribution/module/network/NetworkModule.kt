package com.affise.attribution.module.network

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.converter.StringToMD5Converter
import com.affise.attribution.converter.StringToSHA1Converter
import com.affise.attribution.module.network.parameters.ConnectionTypeProvider
import com.affise.attribution.module.network.parameters.MacMD5Provider
import com.affise.attribution.module.network.parameters.MacProvider
import com.affise.attribution.module.network.parameters.MacSha1Provider
import com.affise.attribution.module.network.parameters.ProxyIpAddressProvider
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.parameters.base.PropertyProvider


class NetworkModule : AffiseModule() {

    override val version: String = BuildConfig.AFFISE_VERSION

    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider? by lazy {
        get<BuildConfigPropertiesProvider>()
    }

    private val stringToMD5Converter: StringToMD5Converter? by lazy {
        get<StringToMD5Converter>()
    }

    private val stringToSHA1Converter: StringToSHA1Converter? by lazy {
        get<StringToSHA1Converter>()
    }

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

    private val macProvider: MacProvider? by lazy {
        logsManager?.let {
            MacProvider(it)
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

    private var providers: List<PropertyProvider<*>> = emptyList()

    override fun start() {
        providers = listOfNotNull(
            connectionTypeProvider,
            proxyIpAddressProvider,
            macMD5Provider,
            macSha1Provider,
        )
    }

    override fun providers(): List<PropertyProvider<*>> = providers
}