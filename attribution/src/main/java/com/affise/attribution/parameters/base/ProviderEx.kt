package com.affise.attribution.parameters.base

import com.affise.attribution.parameters.providers.AffAppTokenPropertyProvider
import com.affise.attribution.parameters.providers.CreatedTimeProvider
import com.affise.attribution.parameters.ProviderType


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