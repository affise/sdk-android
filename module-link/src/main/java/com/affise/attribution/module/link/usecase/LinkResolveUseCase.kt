package com.affise.attribution.module.link.usecase

import com.affise.attribution.modules.link.AffiseLinkCallback

internal interface LinkResolveUseCase {
    fun linkResolve(url: String, callback: AffiseLinkCallback)
}