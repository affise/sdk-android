package com.affise.attribution.converter

import com.affise.attribution.events.predefined.AffiseLog
import com.affise.attribution.logs.SerializedLog
import com.affise.attribution.utils.generateUUID
import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Converter AffiseLog to SerializedLog
 */
class LogToSerializedLogConverter : Converter<AffiseLog, SerializedLog> {

    /**
     * Convert [from] AffiseLog to SerializedLog
     */
    override fun convert(from: AffiseLog): SerializedLog {
        //Generate id
        val id = generateUUID().toString()

        //Type of log
        val type = from.name.type

        //Generate parameters
        val value: Any = when (from) {
            is AffiseLog.NetworkLog -> from.jsonObject
            else -> from.value
        }

        //Create JSONObject for parameters
        val parameters = JSONObject().apply {
            put(type, value)
        }

        //Generate data
        val json = JSONObject().apply {
            //Add id
            put("affise_sdkevent_id", id)

            //Add name
            put("affise_sdkevent_name", "affise_event_sdklog")

            //Add timestam
            put("affise_sdkevent_timestamp", timestamp())

            //Add parameters
            put("affise_sdkevent_parameters", parameters)
        }

        //Create serialized object
        return SerializedLog(id, from.name.type, json)
    }
}