package com.affise.app.application

import android.content.SharedPreferences
import com.affise.app.dependencies.DaggerAppComponent
import com.affise.attribution.Affise
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
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
                affiseAppId = prefString(AFFISE_APP_ID_KEY, "129"),
                secretKey = prefString(SECRET_ID_KEY, "93a40b54-6f12-443f-a250-ebf67c5ee4d2")
            )
            //To enable debug methods set Production to false
            .setProduction(prefBoolean(PRODUCTION_KEY,false))
            // Custom domain example
            // Url trailing slash is irrelevant
            .setDomain(prefString(DOMAIN_KEY, "https://tracking.affattr.com"))
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
//        Affise.getReferrer {
//            println("Referrer: $it")
//        }

        // Get providers https://github.com/affise/sdk-android#get-providers
//        val providers = Affise.getProviders().entries.associate { it.key.provider to it.value }
//        println("Providers: ${JSONObject(providers).toString(4)}")

        // Debug: Validate credentials https://github.com/affise/sdk-android#validate-credentials
//        Affise.Debug.validate {
//            println("Affise: validate = $it")
//        }

        debugRequest = prefBoolean(DEBUG_REQUEST_KEY)
        debugResponse = prefBoolean(DEBUG_RESPONSE_KEY)
        // Debug: network request/response
        Affise.Debug.network { request, response ->
            if (debugRequest) {
                println("Affise: $request")
            }
            if (debugResponse) {
                println("Affise: $response")
            }
        }
    }

    private fun prefString(key: String, default: String): String {
        val value = preferences.getString(key, null)
        if (value.isNullOrBlank()) {
            return default
        }
        return value
    }

    private fun prefBoolean(key: String, default: Boolean = false): Boolean {
        return preferences.getBoolean(key, default)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .application(this)
            .build()

    companion object {
        const val AFFISE_APP_ID_KEY = "AFFISE_APP_ID_KEY"
        const val SECRET_ID_KEY = "SECRET_ID_KEY"
        const val PRODUCTION_KEY = "PRODUCTION_KEY"
        const val DOMAIN_KEY = "DOMAIN_KEY"
        const val AUTO_CATCHING_TYPES_KEY = "AUTO_CATCHING_TYPES_KEY"
        const val ENABLED_METRICS_KEY = "ENABLED_METRICS_KEY"
        const val DEBUG_REQUEST_KEY = "DEBUG_REQUEST_KEY"
        const val DEBUG_RESPONSE_KEY = "DEBUG_RESPONSE_KEY"

        var debugRequest = false
        var debugResponse = false
    }
}