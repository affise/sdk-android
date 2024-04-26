package com.affise.attribution.module.androidid

import com.affise.attribution.converter.StringToMD5Converter
import com.affise.attribution.module.androidid.parameters.AndroidIdMD5Provider
import com.affise.attribution.module.androidid.parameters.AndroidIdProvider
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.parameters.base.StringPropertyProvider


class AndroidIdModule : AffiseModule() {

    private val stringToMD5Converter: StringToMD5Converter? by lazy {
        get<StringToMD5Converter>()
    }

    private val androidIdProvider: StringPropertyProvider? by lazy {
        application?.let { app ->
            AndroidIdProvider(app)
        }
    }

    private val androidIdMD5Provider: PropertyProvider<*>? by lazy {
        androidIdProvider?.let { mac ->
            stringToMD5Converter?.let { md5 ->
                AndroidIdMD5Provider(mac, md5)
            }
        }
    }

    private var providers: List<PropertyProvider<*>> = emptyList()

    override fun start() {
        providers = listOfNotNull(
            androidIdProvider,
            androidIdMD5Provider
        )
    }

    override fun providers(): List<PropertyProvider<*>> = providers
}