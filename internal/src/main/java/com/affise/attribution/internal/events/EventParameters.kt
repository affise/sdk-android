package com.affise.attribution.internal.events

import com.affise.attribution.events.Event
import com.affise.attribution.events.parameters.Predefined
import com.affise.attribution.events.parameters.PredefinedFloat
import com.affise.attribution.events.parameters.PredefinedGroup
import com.affise.attribution.events.parameters.PredefinedListObject
import com.affise.attribution.events.parameters.PredefinedListString
import com.affise.attribution.events.parameters.PredefinedLong
import com.affise.attribution.events.parameters.PredefinedObject
import com.affise.attribution.events.parameters.PredefinedParameter
import com.affise.attribution.events.parameters.PredefinedSimple
import com.affise.attribution.events.parameters.PredefinedString
import com.affise.attribution.internal.utils.toJSONObject

internal object EventParameters {
    fun addParameters(event: Event?, map: Map<*, *>?) {
        event ?: return
        map ?: return
        parsePredefinedData(event, map)
    }

    private fun parsePredefinedData(any: Any, map: Map<*, *>) {
        for ((key, value) in map) {
            key ?: continue
            value ?: continue
            if (key !is String) continue
            try {
                getPredefinedType(key)?.let { type ->
                    when (any) {
                        is PredefinedParameter -> any.addPredefinedValue(type, value)
                        is PredefinedSimple -> any.addPredefinedSimpleValue(type, value)
                    }
                }
                if (key == PredefinedGroup.NAME) {
                    (any as? PredefinedParameter)?.addPredefinedListGroupValue(value)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getPredefinedType(name: String): Predefined? {
        return PredefinedString.from(name)
            ?: PredefinedListString.from(name)
            ?: PredefinedLong.from(name)
            ?: PredefinedFloat.from(name)
            ?: PredefinedObject.from(name)
            ?: PredefinedListObject.from(name)
    }

    private fun PredefinedSimple.addPredefinedSimpleValue(type: Predefined, value: Any) {
        when (type) {
            is PredefinedString -> (value.toString()).let {
                this.addPredefinedParameter(type, it)
            }

            is PredefinedLong -> (value as? Number)?.toLong()?.let {
                this.addPredefinedParameter(type, it)
            }

            is PredefinedFloat -> (value as? Number)?.toFloat()?.let {
                this.addPredefinedParameter(type, it)
            }
        }
    }

    private fun PredefinedParameter.addPredefinedValue(type: Predefined, value: Any) {
        when (type) {
            is PredefinedString, is PredefinedLong, is PredefinedFloat -> {
                (this as? PredefinedSimple)?.addPredefinedSimpleValue(type, value)
            }

            is PredefinedListString -> (value as? List<*>)
                ?.mapNotNull { it.toString() }
                ?.let {
                    this.addPredefinedParameter(type, it)
                }

            is PredefinedObject -> (value as? Map<*, *>)
                ?.toJSONObject()
                ?.let {
                    this.addPredefinedParameter(type, it)
                }

            is PredefinedListObject -> (value as? List<*>)
                ?.mapNotNull {
                    (it as? Map<*, *>)?.toJSONObject()
                }?.let {
                    this.addPredefinedParameter(type, it)
                }
        }
    }

    private fun PredefinedParameter.addPredefinedListGroupValue(value: Any) {
        val result = mutableListOf<PredefinedGroup>()
        (value as? List<*>)
            ?.mapNotNull { it as? Map<*, *> }
            ?.forEach {
                PredefinedGroup().apply {
                    parsePredefinedData(this, it)
                    result.add(this)
                }
            }
        // TODO PredefinedListGroup
//        this.addPredefinedListGroup(result)
    }

}