package com.affise.attribution.modules.link

import com.affise.attribution.modules.AffiseModuleApi

interface AffiseLinkApi : AffiseModuleApi {
    fun resolve(url: String, callback: AffiseLinkCallback)
}