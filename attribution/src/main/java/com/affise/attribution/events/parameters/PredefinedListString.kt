package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains List of Strings
 *
 * @property value the key of parameter
 */
enum class PredefinedListString(private val value: String): Predefined {
    CONTENT_IDS("content_ids");

    override fun value(): String = "${Predefined.PREFIX}${this.value}"

    companion object {
        @JvmStatic
        fun from(name: String?): PredefinedListString? = name?.let { value ->
            PredefinedListString.values().firstOrNull { "${Predefined.PREFIX}${it.value}" == value }
        }
    }
}