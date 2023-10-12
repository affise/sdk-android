package com.affise.attribution.parameters.base

import com.affise.attribution.parameters.providers.AffAppTokenPropertyProvider
import com.affise.attribution.parameters.providers.CreatedTimeProvider
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.providers.AffiseAppIdProvider
import com.affise.attribution.parameters.providers.AffiseDeviceIdProvider
import com.affise.attribution.parameters.providers.AffisePackageAppNameProvider
import com.affise.attribution.parameters.providers.RandomUserIdProvider


inline fun <reified T: Provider> List<Provider>.getProvider(): T? {
    return this.firstOrNull { it is T } as? T
}

fun List<Provider>.mapProviders(): Map<ProviderType, Any?> {
    val createdTime = this.getProvider<CreatedTimeProvider>()?.provideWithDefault()
    val sorted = this.sortedBy { it.order }.filter { it.key != null }
    return sorted.mapNotNull { provider ->
        provider.key?.let {
            it to when (provider) {
                is CreatedTimeProvider -> createdTime
                is PropertyProvider<*> -> provider.provideWithDefault()
                is StringWithParamPropertyProvider -> {
                    when (provider) {
                        is AffAppTokenPropertyProvider -> provider.provideWithParamAndDefault(
                            createdTime?.toString() ?: ""
                        )
                        else -> null
                    }
                }
                else -> null
            }
        }
    }.toMap()
}

fun List<Provider>.getProviders(types: List<Class<out Provider>>): List<Provider> {
    return this.filter {
        types.contains(it::class.java)
    }
}

fun List<Provider>.getRequestProviders(): List<Provider> {
    return this.getProviders(
        listOf(
            CreatedTimeProvider::class.java,
            AffiseAppIdProvider::class.java,
            AffisePackageAppNameProvider::class.java,
            AffAppTokenPropertyProvider::class.java,
            AffiseDeviceIdProvider::class.java,
            RandomUserIdProvider::class.java,
        )
    )
}