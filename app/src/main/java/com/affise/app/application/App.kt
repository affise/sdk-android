package com.affise.app.application

import android.content.SharedPreferences
import android.util.Log
import com.affise.app.dependencies.DaggerAppComponent
import com.affise.attribution.Affise
import com.affise.attribution.init.AffiseInitProperties
import com.affise.attribution.modules.AffiseModules
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.json.JSONObject
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        val props = AffiseInitProperties(
            affiseAppId = "129",
            secretKey = "93a40b54-6f12-443f-a250-ebf67c5ee4d2",
        )

        Affise.init(this, props)

//        Affise.getStatus(AffiseModules.Status) {
//            Log.d(this.javaClass.simpleName, "Status: $it")
//        }
//
//        Affise.getReferrerValue(ReferrerKey.CAMPAIGN_ID) {
//            Log.d(this.javaClass.simpleName, "Referrer CAMPAIGN_ID: $it")
//        }
//
//        Affise.getReferrerValue(ReferrerKey.AD_ID) {
//            Log.d(this.javaClass.simpleName, "Referrer AD_ID: $it")
//        }

        Affise.getReferrer {
            Log.d(this.javaClass.simpleName, "Referrer: $it")
        }

//        val providers = Affise.getProviders().entries.associate { it.key.provider to it.value }
//        Log.d(this.javaClass.simpleName, "Providers: ${JSONObject(providers).toString(4)}")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .application(this)
            .build()

    companion object {
        const val SECRET_ID_KEY = "SECRET_ID_KEY"
        const val AUTO_CATCHING_TYPES_KEY = "AUTO_CATCHING_TYPES_KEY"
        const val ENABLED_METRICS_KEY = "ENABLED_METRICS_KEY"
    }
}