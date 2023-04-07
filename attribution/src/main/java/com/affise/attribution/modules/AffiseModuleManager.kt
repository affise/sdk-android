package com.affise.attribution.modules

import android.app.Application
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.factory.PostBackModelFactory


internal class AffiseModuleManager(
    private val application: Application,
    private val logsManager: LogsManager,
    private val postBackModelFactory: PostBackModelFactory,
) {

    fun init(dependencies: List<Any>) {
        AffiseModules.modules.forEach { module ->
            getClass(module)?.let {
                it.init(application, logsManager, dependencies)
                postBackModelFactory.addProviders(it.providers())
            }
        }
    }

    private fun getClass(className: String): AffiseModule? = try {
        Class.forName(className).newInstance() as? AffiseModule
    } catch (_: Exception) {
        null
    }
}