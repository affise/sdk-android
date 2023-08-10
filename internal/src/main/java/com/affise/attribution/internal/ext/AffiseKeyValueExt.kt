package com.affise.attribution.internal.ext

import com.affise.attribution.modules.AffiseKeyValue
import org.json.JSONArray

internal fun List<AffiseKeyValue>.toJSONArray(): JSONArray = JSONArray(this.toListOfMap())

internal fun List<AffiseKeyValue>.toListOfMap(): List<Map<String, String?>> = this.map {
    mapOf(
        "key" to it.key,
        "value" to it.value,
    )
}
