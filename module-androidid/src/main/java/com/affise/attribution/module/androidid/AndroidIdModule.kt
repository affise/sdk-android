package com.affise.attribution.module.androidid

import com.affise.attribution.converter.StringToMD5Converter
import com.affise.attribution.module.androidid.parameters.AndroidIdMD5Provider
import com.affise.attribution.module.androidid.parameters.AndroidIdProvider
import com.affise.attribution.module.androidid.usecase.AndroidIdUseCase
import com.affise.attribution.module.androidid.usecase.AndroidIdUseCaseImpl
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.androidid.AndroidIdApi
import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.parameters.base.StringPropertyProvider


class AndroidIdModule : AffiseModule(), AndroidIdApi {

    override val version: String = BuildConfig.AFFISE_VERSION

    private val stringToMD5Converter: StringToMD5Converter? by lazy {
        get<StringToMD5Converter>()
    }

    private val androidIdUseCase: AndroidIdUseCase? by lazy {
        AndroidIdUseCaseImpl(application)
    }

    private val androidIdProvider: StringPropertyProvider? by lazy {
        androidIdUseCase?.let {
            AndroidIdProvider(it)
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

    override fun getAndroidId(): String? = androidIdUseCase?.getAndroidId()
}