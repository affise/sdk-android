package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains List of JSONObjects
 *
 * @property value the key of parameter
 */
enum class PredefinedListObject(private val value: String): Predefined {
    CONTENT_LIST("affise_p_content_list" );

    override fun value(): String = this.value

    companion object {
        @JvmStatic
        fun from(name: String?): PredefinedListObject? = name?.let { value ->
            PredefinedListObject.values().firstOrNull { it.value == value }
        }
    }
}