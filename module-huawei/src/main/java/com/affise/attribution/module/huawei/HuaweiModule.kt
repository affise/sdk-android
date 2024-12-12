package com.affise.attribution.module.huawei

import android.content.SharedPreferences
import com.affise.attribution.module.huawei.referrer.HuaweiReferrerDataToStringConverter
import com.affise.attribution.module.huawei.referrer.HuaweiReferrerUseCase
import com.affise.attribution.module.huawei.referrer.StringToHuaweiReferrerDataConverter
import com.affise.attribution.modules.AffiseModule
import com.affise.attribution.modules.huawei.AffiseHuaweiApi
import com.affise.attribution.referrer.AffiseReferrerData


internal class HuaweiModule : AffiseModule(), AffiseHuaweiApi {

    override val version: String = BuildConfig.AFFISE_VERSION

    private var huaweiReferrerUseCase: HuaweiReferrerUseCase? = null

    override fun start() {
        huaweiReferrerUseCase = HuaweiReferrerUseCase(
            get<SharedPreferences>(),
            application,
            logsManager,
            HuaweiReferrerDataToStringConverter(),
            StringToHuaweiReferrerDataConverter(logsManager)
        )
    }

    override fun getInstallReferrerData(): AffiseReferrerData? {
        return huaweiReferrerUseCase?.getInstallReferrerData()
    }

    override fun startInstallReferrerRetrieve(onFinished: (() -> Unit)?) {
        huaweiReferrerUseCase?.startInstallReferrerRetrieve(onFinished)
    }
}