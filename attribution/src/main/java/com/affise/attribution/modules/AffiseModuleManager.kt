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
        initAffiseModules { module ->
            module.dependencies(
                application,
                logsManager,
                dependencies,
                postBackModelFactory.getProviders()
            )

            if (!module.isManual) {
                moduleStart(module)
            }
        }
    }

    fun manualStart(module: AffiseModules) : Boolean {
        getModule(module)?.let {
            if (!it.isManual) return false
            moduleStart(it)
            return true
        }
        return false
    }

    fun status(module: AffiseModules, onComplete: OnKeyValueCallback) {
        getModule(module)?.status(onComplete) ?: onComplete.handle(listOf(AffiseKeyValue(module.name, "not found")))
    }

    fun getModules(): List<AffiseModules> {
        return modules.map { it.key }
    }

    private fun getClass(className: String): AffiseModule? = try {
        Class.forName(className).newInstance() as? AffiseModule
    } catch (_: Exception) {
        null
    }

    private fun moduleStart(module: AffiseModule) {
        module.start()
        postBackModelFactory.addProviders(module.providers())
    }

    private fun getModule(module: AffiseModules): AffiseModule? = modules[module]

    private fun initAffiseModules(callback: (AffiseModule) -> Unit) {
        AffiseModules.values().forEach { name ->
            getClass(name.module)?.let { module ->
                modules[name] = module
                callback(module)
            }
        }
    }
}