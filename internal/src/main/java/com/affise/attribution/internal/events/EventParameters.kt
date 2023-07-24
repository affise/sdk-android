package com.affise.attribution.internal.events

import com.affise.attribution.events.Event
import com.affise.attribution.events.parameters.Predefined
import com.affise.attribution.events.parameters.PredefinedFloat
import com.affise.attribution.events.parameters.PredefinedListObject
import com.affise.attribution.events.parameters.PredefinedListString
import com.affise.attribution.events.parameters.PredefinedLong
import com.affise.attribution.events.parameters.PredefinedObject
import com.affise.attribution.events.parameters.PredefinedString
import com.affise.attribution.internal.utils.toJSONObject

internal object EventParameters {
    fun addParameters(event: Event?, map: Map<*, *>?) {
        event ?: return
        map ?: return

        for ((key, value) in map) {
            key ?: continue
            value ?: continue
            if (key !is String) continue
            try {
                event.addPredefinedValue(key, value)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getPredefined(name: String): Predefined? {
        return PredefinedString.from(name)
            ?: PredefinedListString.from(name)
            ?: PredefinedLong.from(name)
            ?: PredefinedFloat.from(name)
            ?: PredefinedObject.from(name)
            ?: PredefinedListObject.from(name)
    }

    private fun Event.addPredefinedValue(name: String, value: Any?) {
        value ?: return
        when (val param = getPredefined(name)) {
            is PredefinedString -> (value.toString()).let {
                this.addPredefinedParameter(param, it)
            }

            is PredefinedListString -> (value as? List<*>)
                ?.mapNotNull { it.toString() }
                ?.let {
                    this.addPredefinedParameter(param, it)
                }

            is PredefinedLong -> (value.toString().toLongOrNull())?.let {
                this.addPredefinedParameter(param, it)
            }

            is PredefinedFloat -> (value.toString().toFloatOrNull())?.let {
                this.addPredefinedParameter(param, it)
            }

            is PredefinedObject -> (value as? Map<*, *>)
                ?.toJSONObject()
                ?.let {
                    this.addPredefinedParameter(param, it)
                }

            is PredefinedListObject -> (value as? List<*>)
                ?.mapNotNull {
                    (it as? Map<*, *>)?.toJSONObject()
                }?.let {
                    this.addPredefinedParameter(param, it)
                }
        }
    }
}