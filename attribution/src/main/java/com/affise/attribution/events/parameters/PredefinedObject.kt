package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains JSONObject
 *
 * @property value the key of parameter
 */
enum class PredefinedObject(private val value: String): Predefined {
    CONTENT("affise_p_content" );

    override fun value(): String = this.value

    companion object {
        @JvmStatic
        fun from(name: String?): PredefinedObject? = name?.let { value ->
            PredefinedObject.values().firstOrNull { it.value == value }
        }
    }
}