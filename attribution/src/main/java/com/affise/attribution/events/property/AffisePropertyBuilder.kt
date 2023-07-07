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

    fun init(name: String, value: Any?): AffisePropertyBuilder = this.also {
        init(name)
        if (name.isNotBlank()) {
            json.put(eventName(), parseValue(value))
        }
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

    private fun eventName(): String = "${PREFIX}_${name}"

    private fun eventProperty(key: String): String = "${eventName()}${SEPARATOR}${key}"

    private fun parseValue(value: Any?): Any? = when (value) {
        is List<*> -> JSONArray().apply {
            value.forEach {
                put(it)
            }
        }

        else -> value
    }

    companion object {
        private const val PREFIX = "affise_event"
        private const val SEPARATOR = "_"
    }
}