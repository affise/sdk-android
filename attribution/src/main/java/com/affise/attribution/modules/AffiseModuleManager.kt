package com.affise.attribution.modules

import android.app.Application
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.factory.PostBackModelFactory


internal class AffiseModuleManager(
    private val application: Application,
    private val logsManager: LogsManager,
    private val postBackModelFactory: PostBackModelFactory,
) {

    private var modules: MutableMap<AffiseModules, AffiseModule> = mutableMapOf()

    fun init(dependencies: List<Any>) {
        AffiseModules.values().forEach { module ->
            getClass(module.module)?.let {
                it.init(application, logsManager, dependencies, postBackModelFactory.getProviders())
                postBackModelFactory.addProviders(it.providers())
                modules[module] = it
            }
        }
    }

    fun status(module: AffiseModules, onComplete: OnKeyValueCallback) {
        modules[module]?.status(onComplete)
    }

    private fun getClass(className: String): AffiseModule? = try {
        Class.forName(className).newInstance() as? AffiseModule
    } catch (_: Exception) {
        null
    }
}