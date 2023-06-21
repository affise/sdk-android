package com.affise.attribution.modules

import android.app.Application
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.parameters.base.Provider

abstract class AffiseModule {

    protected var application: Application? = null
    var dependencies: List<Any>? = null
    var baseProviders: List<Provider>? = null

    fun init(
        application: Application,
        logsManager: LogsManager,
        dependencies: List<Any>,
        providers: List<Provider>
    ) {
        this.dependencies = dependencies
        this.application = application
        this.baseProviders = providers
        init(logsManager)
    }

    fun init(
        application: Application,
        logsManager: LogsManager,
        dependencies: List<Any>
    ) {
        init(
            application = application,
            logsManager = logsManager,
            dependencies = dependencies,
            providers = emptyList()
        )
    }

    abstract fun init(logsManager: LogsManager)

    abstract fun providers(): List<PropertyProvider<*>>

    open fun status(onComplete: OnKeyValueCallback) = Unit

    inline fun <reified T> get(): T? {
        return dependencies?.firstOrNull { it is T } as? T
    }
    inline fun <reified T: Provider> getProvider(): T? {
        return baseProviders?.firstOrNull { it is T } as? T
    }
}

