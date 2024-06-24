package com.affise.attribution.modules.link

import com.affise.attribution.modules.AffiseModuleApi

interface AffiseLinkApi : AffiseModuleApi {
    fun linkResolve(url: String, callback: AffiseLinkCallback)
}