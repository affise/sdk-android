package com.affise.attribution.events.property

import com.affise.attribution.utils.toSnakeCase
import org.json.JSONArray
import org.json.JSONObject

class AffisePropertyBuilder {
    private var json: JSONObject = JSONObject()
    private var name: String? = null

    fun init(name: String): AffisePropertyBuilder = this.also {
        this.name = name.toSnakeCase()
    }

    fun add(key: AffiseProperty, value: Any?): AffisePropertyBuilder = add(key.type, value)

    fun add(key: String, value: Any?): AffisePropertyBuilder = this.also {
        if (!name.isNullOrBlank()) {
            addRaw(eventProperty(key), value)
        }
    }

    fun addRaw(key: String, value: Any?): AffisePropertyBuilder = this.also {
        if (key.isNotBlank()) {
            json.put(key, parseValue(value))
        }
    }

    fun build(): JSONObject = json

    private fun eventName(): String = name.toAffiseEventProperty()

    private fun eventProperty(key: String): String = name.toAffiseEventProperty(key)

    private fun parseValue(value: Any?): Any? = when (value) {
        is List<*> -> JSONArray().apply {
            value.forEach {
                put(it)
            }
        }

        else -> value
    }
}