package com.affise.app.application

import android.content.SharedPreferences
import com.affise.app.dependencies.DaggerAppComponent
import com.affise.attribution.Affise
import com.affise.attribution.events.autoCatchingClick.AutoCatchingType
import com.affise.attribution.init.AffiseInitProperties
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

        val props = AffiseInitProperties(
            affiseAppId = "AffiseDemoApp",
            partParamName = "partParamName",
            partParamNameToken = "partParamNameToken",
            appToken = "app-token",
            isProduction = false,
            secretId = preferences.getString(SECRET_ID_KEY, "be07d122-3f3c-11ec-9bbc-0242ac130002"),
            autoCatchingClickEvents = preferences.getStringSet(App.AUTO_CATCHING_TYPES_KEY, null)
                ?.map { AutoCatchingType.valueOf(it) }
                ?: emptyList(),
            enabledMetrics = preferences.getBoolean(ENABLED_METRICS_KEY, false)
        )

        Affise.init(this, props)
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