package com.affise.attribution.events

import com.affise.attribution.events.parameters.Predefined
import com.affise.attribution.events.parameters.PredefinedFloat
import com.affise.attribution.events.parameters.PredefinedListObject
import com.affise.attribution.events.parameters.PredefinedListString
import com.affise.attribution.events.parameters.PredefinedLong
import com.affise.attribution.events.parameters.PredefinedObject
import com.affise.attribution.events.parameters.PredefinedString
import org.json.JSONObject

/**
 * Base event
 */
abstract class Event {

    /**
     * Event predefined parameters
     */
    private val predefinedParameters = mutableMapOf<Predefined, Any>()

    /**
     * Is first for user, default false
     */
    private var firstForUser: Boolean = false

    /**
     * Serialize event to JSONObject
     *
     * @return JSONObject
     */
    abstract fun serialize(): JSONObject

    /**
     * Name of event
     *
     * @return name
     */
    abstract fun getName(): String

    /**
     * Category of event
     *
     * @return category
     */
    abstract fun getCategory(): String

    /**
     * User data
     *
     * @return userData
     */
    abstract fun getUserData(): String?

    /**
     * Is first for user, default false
     *
     * @return is first for user or not
     */
    fun isFirstForUser(): Boolean = firstForUser

    fun setFirstForUser(firstForUser: Boolean) {
        this.firstForUser = firstForUser
    }

    /**
     * Add predefined [parameter] with [value] of String to event
     */
    fun addPredefinedParameter(parameter: PredefinedString, value: String) {
        predefinedParameters[parameter] = value
    }
    /**
     * Add predefined [parameter] with [value] of List<String> to event
     */
    fun addPredefinedParameter(parameter: PredefinedListString, value: List<String>) {
        predefinedParameters[parameter] = value
    }

    /**
     * Add predefined [parameter] with [value] of Long to event
     */
    fun addPredefinedParameter(parameter: PredefinedLong, value: Long) {
        predefinedParameters[parameter] = value
    }

    /**
     * Add predefined [parameter] with [value] of float to event
     */
    fun addPredefinedParameter(parameter: PredefinedFloat, value: Float) {
        predefinedParameters[parameter] = value
    }

    /**
     * Add predefined [parameter] with [value] of JSONObject to event
     */
    fun addPredefinedParameter(parameter: PredefinedObject, value: JSONObject) {
        predefinedParameters[parameter] = value
    }

    /**
     * Add predefined [parameter] with [value] of List<JSONObject> to event
     */
    fun addPredefinedParameter(parameter: PredefinedListObject, value: List<JSONObject>) {
        predefinedParameters[parameter] = value
    }

    /**
     * Get map of predefined parameter
     *
     * @return map of predefined parameter
     */
    fun getPredefinedParameters(): Map<Predefined, Any> = predefinedParameters.toMap()
}