package com.affise.attribution.events.autoCatchingClick

import com.affise.attribution.converter.StringToSHA1Converter
import com.affise.attribution.events.Event
import org.json.JSONArray
import org.json.JSONObject

class AutoCatchingClickEvent(
    private val isGroup: Boolean,
    private val data: List<AutoCatchingClickData>,
    private val activityName: String,
    private val timeStampMillis: Long = System.currentTimeMillis()
) : Event() {

    override fun serialize(): JSONObject = JSONObject().apply {
        val serializeData = JSONArray().apply {
            data.forEach {
                val item = JSONObject().apply {
                    put("id", it.id ?: "")
                    put("tag", it.tag ?: "")
                    put("text", it.text ?: "")
                    put("view", it.typeView ?: "")
                }

                put(item)
            }
        }

        put("affise_event_auto_catching_group", isGroup)
        put("affise_event_auto_catching_activity", activityName)
        put("affise_event_auto_catching_click", serializeData)
        put("affise_event_auto_catching_click_timestamp", timeStampMillis)
    }

    override fun getName(): String = "AutoCatchingClickEvent_${getEventSha1()}"

    override fun getCategory(): String = "autoNative"

    override fun getUserData(): String = "Auto generate even on click"

    private fun getEventSha1() = StringToSHA1Converter().convert(
        data.fold(activityName) { acc, value ->
            acc + (value.id ?: "")
        }
    )
}