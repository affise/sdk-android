package com.affise.app.application

import android.content.SharedPreferences
import com.affise.app.dependencies.DaggerAppComponent
import com.affise.attribution.Affise
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

        // Initialize https://github.com/affise/sdk-android#initialize
        Affise
            .settings(
                affiseAppId = "129",
                secretKey = "93a40b54-6f12-443f-a250-ebf67c5ee4d2"
            )
            .setProduction(false) //To enable debug methods set Production to false
            // Custom domain example
            // Url trailing slash is irrelevant
            .setDomain("https://tracking.affattr.com")
            .start(this) // Start Affise SDK

        // Get module status https://github.com/affise/sdk-android#get-module-state
//        Affise.getStatus(AffiseModules.Status) {
//            println("Status: $it")
//        }

        // Get referrer parameter https://github.com/affise/sdk-android#get-referrer-parameter
//        Affise.getReferrerValue(ReferrerKey.AD_ID) {
//            println("Referrer AD_ID: $it")
//        }

        // Get referrer https://github.com/affise/sdk-android#get-referrer
        Affise.getReferrer {
            println("Referrer: $it")
        }

        // Get providers https://github.com/affise/sdk-android#get-providers
//        val providers = Affise.getProviders().entries.associate { it.key.provider to it.value }
//        println("Providers: ${JSONObject(providers).toString(4)}")

        // Debug: Validate credentials https://github.com/affise/sdk-android#validate-credentials
        Affise.Debug.validate {
            println("Affise: validate = $it")
        }

        // Debug: network request/response
        Affise.Debug.network { request, response ->
            println("Affise: $request")
            println("Affise: $response")
        }
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