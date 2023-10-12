package com.affise.attribution.converter

import com.affise.attribution.parameters.base.Provider
import com.affise.attribution.parameters.base.mapProviders
import org.json.JSONArray
import org.json.JSONObject

class ProvidersToJsonStringConverter: Converter<List<Provider>, String> {

    override fun convert(from: List<Provider>): String {
        val jsonObject = JSONObject()

        from.mapProviders().forEach {
            jsonObject.put(it.key.provider, it.value)
        }

        //Create json string
        return JSONArray().apply {
            put(jsonObject)
        }
            .toString()
            .replace("\\/", "/")
    }
}