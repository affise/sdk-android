package com.affise.attribution.modules

import android.app.Application
import com.affise.attribution.BuildConfig
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.modules.exceptions.AffiseModuleError
import com.affise.attribution.parameters.factory.PostBackModelFactory


class AffiseModuleManager(
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

    private fun getClassInstance(className: String): AffiseModule? = try {
        Class.forName(className).getDeclaredConstructor().newInstance() as? AffiseModule
    } catch (_: Exception) {
        null
    }

    private fun moduleStart(module: AffiseModule) {
        module.start()
        postBackModelFactory.addProviders(module.providers())
    }

    fun getModule(module: AffiseModules): AffiseModule? = modules[module]

    private fun initAffiseModules(callback: (AffiseModule) -> Unit) {
        AffiseModules.values().forEach { name ->
            getClassInstance(name.module)?.let { module ->
                if (module.version == BuildConfig.AFFISE_VERSION) {
                    modules[name] = module
                    callback(module)
                } else {
                    AffiseModuleError.Version(name, module).printStackTrace()
                }
            }
        }
    }

    fun <API:AffiseModuleApi> api(module: AffiseModules): API? {
        @Suppress("UNCHECKED_CAST")
        return getModule(module) as? API
    }
}