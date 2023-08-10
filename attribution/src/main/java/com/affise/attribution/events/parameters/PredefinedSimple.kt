package com.affise.attribution.events.parameters


interface PredefinedSimple {

    /**
     * Add predefined [parameter] with [value] of String to event
     */
    fun addPredefinedParameter(parameter: PredefinedString, value: String) : PredefinedSimple


    /**
     * Add predefined [parameter] with [value] of Long to event
     */
    fun addPredefinedParameter(parameter: PredefinedLong, value: Long) : PredefinedSimple

    /**
     * Add predefined [parameter] with [value] of float to event
     */
    fun addPredefinedParameter(parameter: PredefinedFloat, value: Float) : PredefinedSimple
}