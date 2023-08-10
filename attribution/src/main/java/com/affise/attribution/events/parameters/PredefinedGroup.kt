package com.affise.attribution.events.parameters

class PredefinedGroup: PredefinedSimple {
    /**
     * Event predefined parameters
     */
    private val predefinedParameters = mutableMapOf<String, Any>()

    /**
     * Add predefined [parameter] with [value] of String to event
     */
    override fun addPredefinedParameter(parameter: PredefinedString, value: String): PredefinedGroup {
        predefinedParameters[parameter.value()] = value
        return this
    }

    /**
     * Add predefined [parameter] with [value] of Long to event
     */
    override fun addPredefinedParameter(parameter: PredefinedLong, value: Long): PredefinedGroup {
        predefinedParameters[parameter.value()] = value
        return this
    }

    /**
     * Add predefined [parameter] with [value] of float to event
     */
    override fun addPredefinedParameter(parameter: PredefinedFloat, value: Float): PredefinedGroup {
        predefinedParameters[parameter.value()] = value
        return this
    }

    /**
     * Get map of predefined parameter
     *
     * @return map of predefined parameter
     */
    internal fun getPredefinedParameters(): Map<String, Any> = predefinedParameters.toMap()

    companion object {
        const val NAME = "${Predefined.PREFIX}list_group"
    }
}