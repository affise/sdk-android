package com.affise.attribution.usecase

interface StoreUseCase {

    fun getStore(): String

    companion object {
        const val GOOGLE = "GooglePlay"
        const val HUAWEI = "AppGalery"
        const val AMAZON = "Amazon"
        const val PREINSTALL = "Preinstall"
        const val APK = "Apk"
        const val RUSTORE = "RuStore"
    }
}