package com.affise.attribution.internal.ext

import com.affise.attribution.internal.AffiseApiMethod
import com.affise.attribution.internal.builder.AffiseBuilderProperty

@Suppress("UNCHECKED_CAST")
internal fun <T> Map<*, *>.opt(key: String): T? {
    return this.getOrDefault(key, null) as? T
}

internal fun <T> Map<*, *>.opt(api: AffiseApiMethod): T? {
    return opt(api.method)
}

internal fun <T> Map<*, *>.opt(property: AffiseBuilderProperty): T? {
    return opt(property.type)
}