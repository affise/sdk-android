package com.affise.attribution.internal.ext

import com.affise.attribution.modules.AffiseKeyValue
import org.json.JSONArray
import org.json.JSONObject

internal fun List<AffiseKeyValue>.toJSONArray(): JSONArray = JSONArray()
    .also { result ->
        this.forEach { keyValue ->
            JSONObject().apply {
                put("key", keyValue.key)
                put("value", keyValue.value)
            }.also {
                result.put(it)
            }
        }
    }
