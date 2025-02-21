package com.affise.attribution.modules.androidid

import com.affise.attribution.modules.AffiseModuleApi

interface AndroidIdApi : AffiseModuleApi {
    fun getAndroidId(): String?
}