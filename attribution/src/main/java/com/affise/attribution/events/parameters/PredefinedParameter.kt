package com.affise.attribution.events.parameters

import org.json.JSONObject


interface PredefinedParameter : PredefinedSimple {
    /**
     * Add predefined [parameter] with [value] of String to event
     */
    override fun addPredefinedParameter(parameter: PredefinedString, value: String) : PredefinedParameter


    /**
     * Add predefined [parameter] with [value] of Long to event
     */
    override fun addPredefinedParameter(parameter: PredefinedLong, value: Long) : PredefinedParameter

    /**
     * Add predefined [parameter] with [value] of float to event
     */
    override fun addPredefinedParameter(parameter: PredefinedFloat, value: Float) : PredefinedParameter

    /**
     * Add predefined [parameter] with [value] of List<String> to event
     */
    fun addPredefinedParameter(parameter: PredefinedListString, value: List<String>) : PredefinedParameter

    /**
     * Add predefined [parameter] with [value] of JSONObject to event
     */
    fun addPredefinedParameter(parameter: PredefinedObject, value: JSONObject) : PredefinedParameter

    /**
     * Add predefined [parameter] with [value] of List<JSONObject> to event
     */
    fun addPredefinedParameter(parameter: PredefinedListObject, value: List<JSONObject>) : PredefinedParameter

    /**
     * Add predefined ListGroup [value] of List<PredefinedGroup> to event
     */
    // PredefinedGroup
//    fun addPredefinedListGroup(value: List<PredefinedGroup>) : PredefinedParameter
}