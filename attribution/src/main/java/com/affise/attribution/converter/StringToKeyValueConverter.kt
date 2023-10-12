package com.affise.attribution.converter

import com.affise.attribution.modules.AffiseKeyValue
import org.json.JSONArray

class StringToKeyValueConverter : Converter<String?, List<AffiseKeyValue>> {

    override fun convert(from: String?): List<AffiseKeyValue> {
        val data = mutableListOf<AffiseKeyValue>()

        if (from.isNullOrEmpty()) {
            return data
        }

        val jsonArray = try {
            JSONArray(from)
        } catch (_: Exception) {
            JSONArray()
        }

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val key = item.optString(KEY)
            val value = item.optString(VALUE)

            if (!key.isNullOrEmpty()) {
                data.add(AffiseKeyValue(key = key, value = value))
            }
        }
        return data
    }

    companion object {
        const val KEY = "key"
        const val VALUE = "value"
    }
}