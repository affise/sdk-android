package com.affise.attribution.module.link

import com.affise.attribution.executors.ExecutorServiceProviderImpl
import com.affise.attribution.module.link.usecase.LinkResolveUseCase
import com.affise.attribution.module.link.usecase.LinkResolveUseCaseImpl
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.exceptions.AffiseModuleError
import com.affise.attribution.modules.link.AffiseLinkApi
import com.affise.attribution.modules.link.AffiseLinkCallback
import com.affise.attribution.network.HttpClient


internal class LinkModule : AffiseModule(), AffiseLinkApi {

    override val version: String = BuildConfig.AFFISE_VERSION

    private var useCase: LinkResolveUseCase? = null

    override fun start() {
        val httpClient = get<HttpClient>()

        if (
            httpClient == null
        ) {
            AffiseModuleError.Init(this).printStackTrace()
            return
        }

        useCase = LinkResolveUseCaseImpl(
            httpClient,
            ExecutorServiceProviderImpl("Link Resolve Worker")
        )
    }

    override fun resolve(url: String, callback: AffiseLinkCallback) {
        useCase?.linkResolve(url, callback) ?: callback.handle("")
    }
}