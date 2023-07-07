package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains List of Strings
 *
 * @property value the key of parameter
 */
enum class PredefinedListString(private val value: String): Predefined {
    CONTENT_IDS("contentIds");

    override fun value(): String = this.value
}