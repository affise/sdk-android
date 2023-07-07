package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains JSONObject
 *
 * @property value the key of parameter
 */
enum class PredefinedObject(private val value: String): Predefined {
    CONTENT("affise_p_content" );

    override fun value(): String = this.value
}