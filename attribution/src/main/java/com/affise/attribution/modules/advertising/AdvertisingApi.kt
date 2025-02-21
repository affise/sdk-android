package com.affise.attribution.modules.advertising

import com.affise.attribution.modules.AffiseModuleApi

interface AdvertisingApi : AffiseModuleApi {
    fun getAdvertisingId(): String?
}