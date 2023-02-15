package com.affise.attribution.events

import com.affise.attribution.events.predefined.PredefinedParameters
import org.json.JSONObject

/**
 * Base event
 */
abstract class Event {

    /**
     * Event predefined parameters
     */
    private val predefinedParameters = mutableMapOf<PredefinedParameters, String>()

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
     * Add predefined [parameter] with [value] to event
     */
    fun addPredefinedParameter(parameter: PredefinedParameters, value: String) {
        predefinedParameters[parameter] = value
    }

    /**
     * Get map of predefined parameter
     *
     * @return map of predefined parameter
     */
    fun getPredefinedParameters(): Map<PredefinedParameters, String> = predefinedParameters.toMap()
}