package com.affise.attribution.module.status.converter

import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.base.Provider
import com.affise.attribution.parameters.base.mapProviders
import org.json.JSONArray
import org.json.JSONObject

internal class ProvidersToJsonStringConverter: Converter<List<Provider>, String> {

    override fun convert(from: List<Provider>): String {
        val jsonObject = JSONObject()

        from.mapProviders().forEach {
            jsonObject.put(it.key, it.value)
        }

        //Create json string
        return JSONArray().apply {
            put(jsonObject)
        }
            .toString()
            .replace("\\/", "/")
    }
}