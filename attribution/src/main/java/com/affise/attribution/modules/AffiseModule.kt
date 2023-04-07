package com.affise.attribution.modules

import android.app.Application
import com.affise.attribution.parameters.base.PropertyProvider

abstract class AffiseModule {

    protected var application: Application? = null
    var dependencies: List<Any>? = null

    fun init(application: Application, logsManager: com.affise.attribution.logs.LogsManager, dependencies: List<Any>) {
        this.dependencies = dependencies
        this.application = application
        init(logsManager)
    }

    abstract fun init(logsManager: com.affise.attribution.logs.LogsManager)

    abstract fun providers(): List<PropertyProvider<*>>

    inline fun <reified T> get(): T? {
        return dependencies?.firstOrNull { it is T } as? T
    }
}

